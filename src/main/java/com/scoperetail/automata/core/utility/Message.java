package com.scoperetail.automata.core.utility;
/**
 * @author scoperetail
 *
 */
public class Message{
	
	public static final String SUPPLIER_TYPE = "supplierType";
	public static final String ORDER_TYPE = "orderType";
	public static final String CUSTOMER_TYPE = "customerType";
	
	String eventName;
	String orderId;
	String payload;
	String supplierType;
	String customerType;
	String orderType;
	
	public Message() {
		super();
	}
	
	public Message(String supplierType,
	String customerType,String eventName, String orderType, String orderId, String payload) {
		super();
		this.eventName = eventName;
		this.orderType = orderType;
		this.orderId = orderId;
		this.payload = payload;
		this.supplierType = supplierType;
		this.customerType = customerType;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		return "Message [eventName=" + eventName + ", orderId=" + orderId + ", payload=" + payload + ", supplierType="
				+ supplierType + ", customerType=" + customerType + ", orderType=" + orderType + "]";
	}
}