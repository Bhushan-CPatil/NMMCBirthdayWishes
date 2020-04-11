package com.ornettech.qcandbirthdaywishes.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ClientCallResponse{

	@SerializedName("ClientList")
	private List<ClientListItem> clientList;

	public void setClientList(List<ClientListItem> clientList){
		this.clientList = clientList;
	}

	public List<ClientListItem> getClientList(){
		return clientList;
	}

	@Override
 	public String toString(){
		return 
			"ClientCallResponse{" + 
			"clientList = '" + clientList + '\'' + 
			"}";
		}
}