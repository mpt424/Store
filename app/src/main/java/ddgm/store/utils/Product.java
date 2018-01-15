package ddgm.store.utils;

/**
 * Created by mpt on 14/01/2018.
 */

public class Product {
    private String department;
    private String name;
    private int SupplierID;
    private int unitInStock;
    private int UnitOnOrder;
    private double UnitPrice;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(int supplierID) {
        SupplierID = supplierID;
    }

    public int getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(int unitInStock) {
        this.unitInStock = unitInStock;
    }

    public int getUnitOnOrder() {
        return UnitOnOrder;
    }

    public void setUnitOnOrder(int unitOnOrder) {
        UnitOnOrder = unitOnOrder;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }
    public Product(){}

    public Product(String department, String name, int supplierID, int unitInStock, int unitOnOrder, double unitPrice) {
        this.department = department;
        this.name = name;
        SupplierID = supplierID;
        this.unitInStock = unitInStock;
        UnitOnOrder = unitOnOrder;
        UnitPrice = unitPrice;
    }



}
