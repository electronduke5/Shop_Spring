package com.example.Shop.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart", unique = true, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id", referencedColumnName = "id_customer")
    private Customer customer;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> items;

    @ManyToOne
    @JoinColumn(name = "code_id")
    private PromoCode code;

    @Transient
    private Integer size;

    @Transient
    private Double itemsPrice;

    @Transient
    private Double totalPrice;

    public CartItem addProduct(Product product) {
        var existing = items.stream().filter(item1 -> item1.getProduct().getId().equals(product.getId())).toList();

        if (!existing.isEmpty()) {
            var ex = existing.stream().findFirst().get().getId().intValue();
            items.get(ex).setCount(items.get(ex).getCount() + 1);
            return existing.get(ex);
        } else {
            return new CartItem(product, 1, this);
        }
    }

    public Double getTotalPrice() {

        Double result = 0.0;

        return  getItemsPrice() - getItemsPrice() * (code == null ? 0.0 : code.getDiscontAmount()) / 100;
    }

    public Double getItemsPrice() {
        double sum = 0;
        for (var item : items) {
            sum += item.getProduct().getPrice() * item.getCount();
        }
        System.out.println("getItemsPrice = " + sum);
        return sum;
    }

    public Integer getSize() {
        int size = 0;
        for (var item : items) {
            size += item.getCount();
        }
        return size;
    }

    public Cart() {
    }

    public Cart(Customer customer, List<CartItem> items, PromoCode code) {
        this.customer = customer;
        this.items = items;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public PromoCode getCode() {
        return code;
    }

    public void setCode(PromoCode code) {
        this.code = code;
    }
}
