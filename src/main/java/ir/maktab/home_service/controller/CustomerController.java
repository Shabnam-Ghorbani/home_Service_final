package ir.maktab.home_service.controller;

import ir.maktab.home_service.capcha.ReCaptchaResponse;
import ir.maktab.home_service.data.model.entity.Comment;
import ir.maktab.home_service.data.model.entity.Customer;
import ir.maktab.home_service.data.model.entity.Order;
import ir.maktab.home_service.dto.customer.CustomerOrderStatusDto;
import ir.maktab.home_service.dto.expertdto.OrderExpertCommentDTO;
import ir.maktab.home_service.dto.offerdto.OfferDto;
import ir.maktab.home_service.dto.orderdto.OrderDto;
import ir.maktab.home_service.dto.passdto.PasswordDTO;
import ir.maktab.home_service.dto.persondto.PersonDto;
import ir.maktab.home_service.dto.servicedto.SubServiceOrderDTO;
import ir.maktab.home_service.service.impl.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final RestTemplate restTemplate;
    private final ModelMapper mapper;

    public CustomerController(CustomerService customerService, RestTemplate restTemplate, ModelMapper mapper) {
        this.customerService = customerService;
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }


    @GetMapping("/findByEmail/{emailAddress}")
    @ResponseBody
    public PersonDto findByEmail(@PathVariable String emailAddress) {
        String customer = customerService.findByEmailAddress(emailAddress).getEmailAddress();
        return mapper.map(customer, PersonDto.class);
    }

    @GetMapping("/existsByEmail/{emailAddress}")
    @ResponseBody
    public boolean existsByEmail(@PathVariable String emailAddress) {
        return customerService.existsByEmail(emailAddress);
    }

    @PostMapping("/save")
    @ResponseBody
    public void save(@Valid @RequestBody PersonDto personDTO) {
        customerService.save(
                Customer.builder()
                        .firstname(personDTO.getFirstname())
                        .lastname(personDTO.getLastname())
                        .emailAddress(personDTO.getEmail())
                        .password(personDTO.getPassword())
                        .build()
        );
    }

    @PostMapping("/editPassword")
    @ResponseBody
    public int editPassword(@Valid @RequestBody PasswordDTO passwordDTO, Authentication authentication) {
        Customer authenticatedCustomer = (Customer) authentication.getPrincipal();
        return customerService.editPassword(authenticatedCustomer.getId(), passwordDTO.getPassword());
    }

    @PostMapping("/addOrder")
    @ResponseBody
    public void addOrder(@RequestBody SubServiceOrderDTO subServiceOrderDTO, Authentication authentication) {
        Customer authenticatedCustomer = (Customer) authentication.getPrincipal();
        customerService.addOrder(authenticatedCustomer.getId(), subServiceOrderDTO.getId(),
                Order.builder()
                        .proposedPrice(subServiceOrderDTO.getProposedPrice())
                        .description(subServiceOrderDTO.getDescription())
                        .executionTime(LocalDateTime.of(
                                LocalDate.parse(subServiceOrderDTO.getExecutionDate()),
                                LocalTime.parse(subServiceOrderDTO.getExecutionTime())))
                        .build());
    }

    @PostMapping("/addComment")
    @ResponseBody
    public void addCommentForExpertPerformance(@RequestBody OrderExpertCommentDTO orderExpertCommentDTO) {
        customerService.setCommentForExpert(orderExpertCommentDTO.getOrderId(), orderExpertCommentDTO.getExpertId(),
                Comment.builder()
                        .score(Double.valueOf(orderExpertCommentDTO.getScore()))
                        .comment(orderExpertCommentDTO.getComment())
                        .build());
    }

    @GetMapping("/viewOffersBaseOnProposedPrice/{orderId}")
    @ResponseBody
    public List<OfferDto> viewOrderOffersBaseOnProposedPrice(@PathVariable Integer orderId) {
        List<OfferDto> offerDTOS = new ArrayList<>();
        customerService.viewOrderOffersBaseOnProposedPrice(orderId).forEach(offer -> {
            offerDTOS.add(new OfferDto(offer.getProposedPrice(),
                    offer.getTimeType(),
                    offer.getDurationTime(),
                    offer.getExpert().getScore(),
                    offer.getEndTime()));
        });
        return offerDTOS;
    }

    @GetMapping("/viewOffersBaseOnExpertScore/{orderId}")
    @ResponseBody
    public List<OfferDto> viewOrderOffersBaseOnExpertScore(@PathVariable Integer orderId) {
        List<OfferDto> offerDTOS = new ArrayList<>();
        customerService.viewOrderOffersBaseOnExpertScore(orderId).forEach(offer -> {
            offerDTOS.add(new OfferDto(offer.getProposedPrice(),
                    offer.getTimeType(),
                    offer.getDurationTime(),
                    offer.getExpert().getScore(),
                    offer.getEndTime()));
        });
        return offerDTOS;
    }

    @PutMapping("/selectOffer/{offerId}")
    @ResponseBody
    public void selectOffer(@PathVariable Integer offerId) {
        customerService.selectOffer(offerId);
    }

    @PutMapping("/changeOrderStatus/afterExpertComes/{id}")
    @ResponseBody
    public int changeOrderStatusAfterExpertComes(@PathVariable Integer id) {
        return customerService.changeOrderStatusAfterExpertComes(id);
    }

    @PutMapping("/changeOrderStatus/afterStarted/{id}")
    @ResponseBody
    public int changeOrderStatusAfterStarted(@PathVariable Integer id) {
        return customerService.changeOrderStatusAfterStarted(id);
    }

    @PutMapping("/payFromCredit/{orderId}/{expertId}/{amount}")
    @ResponseBody
    public void payFromCredit(@PathVariable Integer orderId, @PathVariable Integer expertId, @PathVariable Long amount,
                              Authentication authentication) {
        Customer authenticatedCustomer = (Customer) authentication.getPrincipal();
        customerService.payFromCredit(orderId, authenticatedCustomer.getId(), expertId, amount);
    }

    @GetMapping("/onlinePay")
    private String onlinePay() {
        return "payment";
    }

    @PostMapping("/onlinePay")
    @ResponseBody
    private String pay(@RequestParam("g-recaptcha-response") String captcha) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret=6LcKj7gjAAAAAAYY0U0irX6rLHFUuETgBmxZtjO1&response=" + captcha;
        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url + params, HttpMethod.POST, null,
                ReCaptchaResponse.class).getBody();
        if (reCaptchaResponse.isSuccess()) {
            return "done";
        }
        return "invalid";
    }

    @GetMapping("/viewOrderHistory")
    @ResponseBody
    public List<OrderDto> viewOrderHistory(Authentication authentication) {
        Customer authenticatedCustomer = (Customer) authentication.getPrincipal();
        List<OrderDto> orderDtos = new ArrayList<>();
        customerService.viewOrderHistory(authenticatedCustomer.getId())
                .forEach(
                        order -> orderDtos.add(
                                mapper.map(order, OrderDto.class))
                );
        return orderDtos;
    }

    @PostMapping("/viewOrderHistory")
    @ResponseBody
    public List<OrderDto> viewOrderHistory(@RequestBody CustomerOrderStatusDto customerOrderStatusDTO, Authentication authentication) {
        Customer authenticatedCustomer = (Customer) authentication.getPrincipal();
        List<OrderDto> orderDTOS = new ArrayList<>();
        customerService
                .viewOrderHistory(
                        authenticatedCustomer.getId(),
                        customerOrderStatusDTO.getOrderStatus())
                .forEach(order -> orderDTOS.add(mapper.map(order, OrderDto.class)));
        return orderDTOS;
    }

    @GetMapping("/viewCredit")
    public Long viewCredit(Authentication authentication) {
        Customer authenticatedCustomer = (Customer) authentication.getPrincipal();
        return customerService.viewCredit(authenticatedCustomer.getId());
    }
}
