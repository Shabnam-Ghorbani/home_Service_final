package ir.maktab.home_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.Offer;
import ir.maktab.home_service.dto.expertdto.ExpertOrderHistoryDTO;
import ir.maktab.home_service.dto.expertdto.OrderExpertOfferDTO;
import ir.maktab.home_service.dto.offerdto.OfferDto;
import ir.maktab.home_service.dto.orderdto.OrderDto;
import ir.maktab.home_service.dto.persondto.PersonDto;
import ir.maktab.home_service.service.impl.ExpertService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final ExpertService expertService;
    private final ModelMapper mapper;

    public ExpertController(ExpertService expertService, ModelMapper mapper) {
        this.expertService = expertService;
        this.mapper = mapper;
    }

    @PostMapping("/signup")
    void signUp(@RequestParam("expert") String expert, @RequestParam("image") MultipartFile multipartFile) throws FileNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PersonDto personDTO = objectMapper.readValue(expert, PersonDto.class);
            expertService.signUp(Expert.builder()
                            .firstname(personDTO.getFirstname())
                            .lastname(personDTO.getLastname())
                    .emailAddress(personDTO.getEmail())
                            .password(personDTO.getPassword())
                    .photo(multipartFile.getBytes())
                            .build(),
                    multipartFile.getOriginalFilename(), multipartFile.getSize());
        } catch (IOException e) {
            throw new FileNotFoundException("this file not found!");
        }
    }

    @PostMapping("/addOfferForOrder")
    public void addOfferForOrder(@RequestBody OrderExpertOfferDTO orderExpertOfferDTO, Authentication authentication) {
        Expert authenticatedExpert = (Expert) authentication.getPrincipal();
        expertService.addOfferForOrder(authenticatedExpert.getId(), orderExpertOfferDTO.getId(),
                Offer.builder()
                        .offer(orderExpertOfferDTO.getOffer())
                        .proposedPrice(orderExpertOfferDTO.getProposedPrice())
                        .timeType(orderExpertOfferDTO.getTimeType())
                        .durationTime(orderExpertOfferDTO.getDurationTime())
                        .endTime(LocalDateTime.of(
                                LocalDate.parse(orderExpertOfferDTO.getEndDate()),
                                LocalTime.parse(orderExpertOfferDTO.getEndTime())))
                        .build());
    }

    @GetMapping("/viewExpertScore")
    public Double viewExpertScore(Authentication authentication) {
        Expert authenticatedExpert = (Expert) authentication.getPrincipal();
        return expertService.viewExpertScore(authenticatedExpert.getId());
    }

    @GetMapping("/viewExpertScoreFromCustomerComment/{commentId}")
    public Double viewExpertScoreFromCustomerComment(@PathVariable Integer commentId) {
        return expertService.viewExpertScoreFromCustomerComment(commentId);
    }

    @GetMapping("/viewOfferHistory/{isAccept}")
    public List<OfferDto> viewOfferHistory(@PathVariable boolean isAccept, Authentication authentication) {
        Expert authenticatedExpert = (Expert) authentication.getPrincipal();
        List<OfferDto> offerDTOS = new ArrayList<>();
        expertService.viewOfferHistory(authenticatedExpert.getId(), isAccept)
                .forEach(
                        offer -> offerDTOS.add(
                                mapper.map(offer, OfferDto.class)));
        return offerDTOS;
    }

    @GetMapping("/viewOrderHistory/{isAccept}")
    public List<OrderDto> viewOrderHistory(@PathVariable boolean isAccept, Authentication authentication) {
        Expert authenticatedExpert = (Expert) authentication.getPrincipal();
        List<OrderDto> orderDTOS = new ArrayList<>();
        expertService.viewOrderHistory(authenticatedExpert.getId(), isAccept)
                .forEach(
                        order -> orderDTOS.add(
                                mapper.map(order, OrderDto.class)));
        return orderDTOS;
    }

    @PostMapping("/viewOrderHistory")
    public List<OrderDto> viewOrderHistory(@RequestBody ExpertOrderHistoryDTO expertOrderHistoryDTO, Authentication authentication) {
        Expert authenticatedExpert = (Expert) authentication.getPrincipal();
        List<OrderDto> orderDTOS = new ArrayList<>();
        expertService.viewOrderHistory(authenticatedExpert.getId(), expertOrderHistoryDTO.getIsAccept(), expertOrderHistoryDTO.getOrderStatus())
                .forEach(
                        order -> orderDTOS.add(
                                mapper.map(order, OrderDto.class)));
        return orderDTOS;
    }

    @GetMapping("/viewCredit")
    public Long viewCredit(Authentication authentication) {
        Expert authenticatedExpert = (Expert) authentication.getPrincipal();
        return expertService.viewCredit(authenticatedExpert.getId());
    }
}
