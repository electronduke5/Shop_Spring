package com.example.Shop.models;

import javax.persistence.*;
import java.util.List;
import java.util.stream.IntStream;

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
        if(items.size() == 0){
            return new CartItem(product, 1, this);
        }
        var existing = IntStream.range(0, items.size()).filter(index -> items.get(index).getProduct().getId().equals(product.getId())).findFirst();
        if (existing.isPresent()) {
            var item = new CartItem(product, items.get(existing.getAsInt()).getCount() + 1, this);
            item.setId(items.get(existing.getAsInt()).getId());
            items.set(existing.getAsInt(), item);
            return items.get(existing.getAsInt());
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
