package ext.modular.product;

import ext.modular.common.BasicEntity;

public class ProductEntity extends BasicEntity {
    private String name;
    private String model_id;
    private String product_code;
    private String model_type;
    private String batch;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "name='" + name + '\'' +
                ", model_id='" + model_id + '\'' +
                ", product_code='" + product_code + '\'' +
                ", model_type='" + model_type + '\'' +
                ", batch='" + batch + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

