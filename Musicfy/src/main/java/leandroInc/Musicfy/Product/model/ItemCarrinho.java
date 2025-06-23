package leandroInc.Musicfy.Product.model;

import jakarta.persistence.Entity;


public class ItemCarrinho {
    private Product product;
    private int quantidade;

    public ItemCarrinho(Product product, int quantidade) {
        this.product = product;
        this.quantidade = quantidade;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void incrementarQuantidade(int valor) {
        this.quantidade += valor;
    }
}