package com.example.Shop.controllers.customer;

import com.example.Shop.models.*;
import com.example.Shop.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/customer/cart")
@PreAuthorize("hasAnyAuthority('User')")
public class CustomerCartController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    PromocodeRepository promocodeRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    PointRepository pointRepository;

    @GetMapping
    public String viewCart(Principal user, Model model) {
        Customer customer = customerRepository.findByUser_Login(user.getName());
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        if (cart == null) {
            cartRepository.save(new Cart(customer, null, null));
        }
        System.out.println("total Price: " + cart.getTotalPrice());
        model.addAttribute("cart", cart);
        return "customer/cart/cart_view";

    }

    @PostMapping("/apply-code")
    public String apply(@RequestParam("code") String code, Principal user) {
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        PromoCode promoCode = promocodeRepository.findByCode(code);

        if (promoCode != null && promoCode.getActive()) {
            cart.setCode(promoCode);
            cartRepository.save(cart);
        }
        return "redirect:/customer/cart";
    }

    @PostMapping("/remove-code")
    public String apply(Principal user, Model model) {

        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        cart.setCode(null);
        cartRepository.save(cart);
        return "redirect:/customer/cart";
    }

    @PostMapping("/plus/{id}")
    public String plus(@PathVariable("id") Long itemId, Principal user) {
        Optional<CartItem> item = cartItemRepository.findById(itemId);
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());

        if(item.get().getCount() < item.get().getProduct().getCount()){
            item.get().setCount(item.get().getCount()+1);
        }
        cartItemRepository.save(item.get());
        return "redirect:/customer/cart";
    }

    @PostMapping("/minus/{id}")
    public String minus(@PathVariable("id") Long itemId, Principal user) {
        Optional<CartItem> item = cartItemRepository.findById(itemId);
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());

        item.get().setCount(item.get().getCount()-1);
        if(item.get().getCount() <= 0){
            cartItemRepository.deleteById(itemId);
        }
        else{
            cartItemRepository.save(item.get());
        }
        return "redirect:/customer/cart";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable("id") Long itemId,
            Principal user) {
        cartItemRepository.deleteById(itemId);
        return "redirect:/customer/cart";
    }

    @GetMapping("/order")
    public String getOrder(CustomOrder order, Principal user, Model model){
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        if(cart.getSize() == 0){
            return "redirect:/customer/cart";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("order", order);
        Iterable<PickupPoint> points = pointRepository.findAll();

        model.addAttribute("points", points);
        return "customer/cart/order";
    }

    @PostMapping("/order")
    public String postOrder(@ModelAttribute("order") @Valid CustomOrder order,
                            BindingResult bindingResult, Principal user, Model model){
        Iterable<PickupPoint> points = pointRepository.findAll();
        Cart cart = cartRepository.findByCustomerUserLogin(user.getName());
        model.addAttribute("points", points);
        if (bindingResult.hasErrors()) {
            return "customer/cart/order";
        }
        cart.setCode(null);
        cartItemRepository.deleteByCartId(cart.getId());
        return "redirect:/customer/orders";


    }
}
