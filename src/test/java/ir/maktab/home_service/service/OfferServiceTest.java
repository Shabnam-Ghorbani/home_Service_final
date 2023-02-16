package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.enamiration.OfferStatus;
import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.Offer;
import ir.maktab.home_service.data.model.entity.Order;
import ir.maktab.home_service.exception.NotMatchException;
import ir.maktab.home_service.service.impl.ExpertService;
import ir.maktab.home_service.service.impl.OfferService;
import ir.maktab.home_service.service.impl.OrderService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OfferServiceTest {
    @Autowired
    OfferService offerService;

    @Autowired
    ExpertService expertService;

    @Autowired
    OrderService orderService;
    Offer offer;

//    @Test
//    @org.junit.jupiter.api.Order(1)
//    public void setOffer_AddOfferToOrder_Test() {
//        Order foundedOrder = orderService.findById(1);
//        Expert expert = expertService.findById(352);
//        Date startDate = null;
//        try {
//            startDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("1401-11-2 18:20");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        offer = Offer.builder()
//                .expert(expert)
//                .order(foundedOrder)
//                .proposedPrice(500000L)
//                .durationOfWork(5)
//                .startTime(startDate)
//                .offerStatus(OfferStatus.UNCHECKED)
//                .build();
//        Order order = offerService.addOfferToOrder(offer);
//        assertTrue(order.getOffers().contains(offer));
//    }

//    @Test
//    @org.junit.jupiter.api.Order(2)
//    public void OfferThatExpertNotHaveThatSubService_Test() {
//        Order order = orderService.findById(1);
//        Expert expert = expertService.findById(352);
//        Date startDate = null;
//        try {
//            startDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("1400-10-15 13:30");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        offer = Offer.builder()
//                .expert(expert)
//                .order(order)
//                .proposedPrice(500000L)
//                .durationOfWork(5)
//                .startTime(startDate)
//                .offerStatus(OfferStatus.UNCHECKED)
//                .build();
//        boolean thrown = assertThrows(NotMatchException.class, () -> offerService.addOfferToOrder(offer)).getMessage().contains("offer is not match for this Order");
//    }

//    @Test
//    @org.junit.jupiter.api.Order(3)
//    public void OfferWhitBasePrice_Test() {
//        Order order = orderService.findById(1);
//        Expert expert = expertService.findById(352);
//        Date startDate = null;
//        try {
//            startDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("1400-10-15 13:30");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        offer = Offer.builder()
//                .expert(expert)
//                .order(order)
//                .proposedPrice(1000L)
//                .durationOfWork(5)
//                .startTime(startDate)
//                .offerStatus(OfferStatus.UNCHECKED)
//                .build();
//        boolean thrown = assertThrows(NotMatchException.class, () -> offerService.addOfferToOrder(offer)).getMessage().contains("offer is not match for this Order");
//    }

//    @Test
//    @org.junit.jupiter.api.Order(4)
//    public void Order_WhenFindByOrder_Test() {
//        Order order = orderService.findById(1);
//        List<Offer> offers = offerService.findByOrder(order);
//        assertTrue(offers.size() == 3);
//        Offer firstOffer = offers.get(0);
//        assertTrue(firstOffer.getExpert().getFirstname().equals("shabnam"));
//        assertTrue(firstOffer.getExpert().getScore() == 5);
//
//    }
}
