package ir.maktab.home_service.controller;

import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import ir.maktab.home_service.data.model.entity.BaseService;
import ir.maktab.home_service.data.model.entity.SubService;
import ir.maktab.home_service.dto.customer.CustomerOrderStatusDto;
import ir.maktab.home_service.dto.expertdto.ExpertActivationDTO;
import ir.maktab.home_service.dto.expertdto.ExpertDto;
import ir.maktab.home_service.dto.expertdto.ExpertOrderHistoryDTO;
import ir.maktab.home_service.dto.filter.CustomerFilterDto;
import ir.maktab.home_service.dto.filter.ExpertFilterDTO;
import ir.maktab.home_service.dto.filter.OrderFilterDTO;
import ir.maktab.home_service.dto.orderdto.OrderDto;
import ir.maktab.home_service.dto.persondto.PersonDto;
import ir.maktab.home_service.dto.servicedto.BaseServiceDto;
import ir.maktab.home_service.dto.servicedto.BaseServiceSubServiceDto;
import ir.maktab.home_service.dto.servicedto.SubServiceDetailDTO;
import ir.maktab.home_service.dto.servicedto.SubServiceDto;
import ir.maktab.home_service.service.impl.ManagerService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final ModelMapper mapper;

    public ManagerController(ManagerService managerService, ModelMapper mapper) {
        this.managerService = managerService;
        this.mapper = mapper;
    }

    @PostMapping("/addBaseService")
    public void addBaseService(@RequestBody BaseServiceDto baseServiceDto) {
        managerService.addBaseService(BaseService.builder().name(baseServiceDto.getName()).build());

    }

    @DeleteMapping("/deleteBAseService/{id}") //ToDo
    public void deleteBAseService(@PathVariable Integer id) {
        managerService.deleteBAseService(id);
    }

    @PostMapping("/addSubService/{id}") //ToDo check
    public void addSubService(@RequestBody BaseServiceSubServiceDto baseServiceSubServiceDto,@PathVariable Integer id) {
        managerService.addSubService(baseServiceSubServiceDto.getBaseServiceId(),
                SubService.builder()
                        .name(baseServiceSubServiceDto.getName())
                        .basePrice(baseServiceSubServiceDto.getBasePrice())
                        .description(baseServiceSubServiceDto.getDescription())
                        .build());
    }

    @DeleteMapping("/deleteSubService/{id}")
    public void deleteSubService(@PathVariable Integer id) {
        managerService.deleteSubService(id);
    }

    @PostMapping("/addExpertToSubService/{id}/{expertId}")//ToDo check
    public void addExpertToSubService(@PathVariable Integer id, @PathVariable Integer expertId) {
        managerService.addExpertToSubService(id, expertId);
    }

    @DeleteMapping("/deleteExpertFromSubService/{subServiceId}/{expertId}")
    public void deleteExpertFromSubService(@PathVariable Integer subServiceId, @PathVariable Integer expertId) {
        managerService.deleteExpertFromSubService(subServiceId, expertId);
    }

    @GetMapping("/findAllBaseService")
    public List<BaseServiceDto> findAllBaseService() {
        List<BaseServiceDto> baseServiceDto = new ArrayList<>();
        managerService.findAllBaseService().forEach(baseService ->
                baseServiceDto.add(mapper.map(baseService, BaseServiceDto.class))
        );
        return baseServiceDto;
    }

    @GetMapping("/findAllSubService")
    public List<SubServiceDto> findAllSubService() {
        List<SubServiceDto> subServiceDTOS = new ArrayList<>();
        managerService.findAllSubService().forEach(subService ->
                subServiceDTOS.add(mapper.map(subService, SubServiceDto.class))
        );
        return subServiceDTOS;
    }

    @PutMapping("/editSubServiceBasePriceAndDescription")
    public int editSubServiceBasePriceAndDescription(@RequestBody SubServiceDetailDTO subServiceDetailDTO) {
        return managerService.editSubServiceBasePriceAndDescription(
                subServiceDetailDTO.getId(),
                subServiceDetailDTO.getBasePrice(),
                subServiceDetailDTO.getDescription());
    }

    @GetMapping("/findAllExpert")
    public List<ExpertDto> findAllExpert() {
        List<ExpertDto> expertDTOS = new ArrayList<>();
        managerService.findAllExpert().forEach(expert ->
                expertDTOS.add(mapper.map(expert, ExpertDto.class))
        );
        return expertDTOS;
    }

    @PutMapping("/checkExpertDelay/{offerId}")
    public boolean checkExpertDelayForDoingWork(@PathVariable Integer offerId) {
        return managerService.checkExpertDelayForDoingWork(offerId);
    }

    @PutMapping("/changeExpertActivation")
    public int changeExpertActivation(@RequestBody ExpertActivationDTO expertActivationDTO) {
        return managerService.changeExpertActivation(expertActivationDTO.getId(), expertActivationDTO.getIsActive());
    }

    @PostMapping("/expertsFilter")
    public List<ExpertDto> expertsFilter(@RequestBody ExpertFilterDTO expertFilterDTO) {
        List<ExpertDto> expertDTOS = new ArrayList<>();
        managerService.expertsFilter(expertFilterDTO)
                .forEach(expert ->
                        expertDTOS.add(mapper.map(expert, ExpertDto.class)));
        return expertDTOS;
    }

    @PostMapping("/customersFilter")
    public List<PersonDto> customersFilter(@RequestBody CustomerFilterDto customerDTO) {
        List<PersonDto> personDTOS = new ArrayList<>();
        managerService.customersFilter(customerDTO).forEach(customer ->
                personDTOS.add(mapper.map(customer, PersonDto.class))
        );
        return personDTOS;
    }

    @Transactional
    @GetMapping("/viewSubServiceExperts/{subServiceId}")
    public Set<ExpertDto> viewSubServiceExperts(@PathVariable Integer subServiceId) {
        Set<ExpertDto> expertDTOS = new HashSet<>();
        managerService.viewSubServiceExperts(subServiceId)
                .forEach(expert ->
                        expertDTOS.add(mapper.map(expert, ExpertDto.class)));
        return expertDTOS;
    }

    @PostMapping("/ordersFilter")
    public List<OrderDto> ordersFilter(@RequestBody OrderFilterDTO orderFilterDTO) {
        List<OrderDto> orderDTOS = new ArrayList<>();
        managerService.ordersFilter(orderFilterDTO)
                .forEach(order ->
                        orderDTOS.add(mapper.map(order, OrderDto.class)));
        return orderDTOS;
    }

    @GetMapping("/viewExpertOrderHistory/{expertId}/{isAccept}")
    public List<OrderDto> viewExpertOrderHistory(@PathVariable Integer expertId, @PathVariable boolean isAccept) {
        List<OrderDto> orderDTOS = new ArrayList<>();
        managerService.viewExpertOrderHistory(expertId, isAccept)
                .forEach(
                        order -> orderDTOS.add(
                                mapper.map(order, OrderDto.class)));
        return orderDTOS;
    }

    @PostMapping("/viewExpertOrderHistory")
    public List<OrderDto> viewExpertOrderHistory(@RequestBody ExpertOrderHistoryDTO expertOrderHistoryDTO) {
        List<OrderDto> orderDTOS = new ArrayList<>();
        managerService.viewExpertOrderHistory(
                        expertOrderHistoryDTO.getId(),
                        expertOrderHistoryDTO.getIsAccept(),
                        expertOrderHistoryDTO.getOrderStatus())
                .forEach(
                        order -> orderDTOS.add(
                                mapper.map(order, OrderDto.class)));
        return orderDTOS;
    }

    @GetMapping("/viewCustomerOrderHistory/{customerId}")
    public List<OrderDto> viewCustomerOrderHistory(@PathVariable Integer customerId) {
        List<OrderDto> orderDTOS = new ArrayList<>();
        managerService.viewCustomerOrderHistory(customerId)
                .forEach(
                        order -> orderDTOS.add(
                                mapper.map(order, OrderDto.class))
                );
        return orderDTOS;
    }

    @PostMapping("/viewCustomerOrderHistory")
    public List<OrderDto> viewCustomerOrderHistory(@RequestBody CustomerOrderStatusDto customerOrderStatusDTO) {
        List<OrderDto> orderDTOS = new ArrayList<>();
        managerService.viewCustomerOrderHistory(
                        customerOrderStatusDTO.getId(),
                        customerOrderStatusDTO.getOrderStatus())
                .forEach(order -> orderDTOS.add(mapper.map(order, OrderDto.class)));
        return orderDTOS;
    }

    @GetMapping("/calculateNumberOfRegisteredOrders/{customerId}")
    public int calculateNumberOfRegisteredOrders(@PathVariable Integer customerId) {
        return managerService.calculateNumberOfRegisteredOrders(customerId);
    }

    @PostMapping("/calculateNumberOfRegisteredOrders")
    public int calculateNumberOfRegisteredOrders(@RequestBody CustomerOrderStatusDto customerOrderStatusDTO) {
        return managerService.calculateNumberOfRegisteredOrders(
                customerOrderStatusDTO.getId(),
                customerOrderStatusDTO.getOrderStatus());
    }

    @GetMapping("/calculateNumberOfPlacedOrders/{expertId}")
    public int calculateNumberOfPlacedOrders(@PathVariable Integer expertId) {
        return managerService.calculateNumberOfPlacedOrders(expertId);
    }

    @GetMapping("/calculateNumberOfPlacedOrders/{expertId}/{isAccept}")
    public int calculateNumberOfPlacedOrders(@PathVariable Integer expertId, @PathVariable Boolean isAccept) {
        return managerService.calculateNumberOfPlacedOrders(expertId, isAccept);
    }
}
