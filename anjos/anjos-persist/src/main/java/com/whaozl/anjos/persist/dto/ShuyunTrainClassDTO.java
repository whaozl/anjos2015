package com.whaozl.anjos.persist.dto;

public class ShuyunTrainClassDTO {
	private Integer cid;
	private Integer rootCid;
	
	public ShuyunTrainClassDTO(){}
	
	public ShuyunTrainClassDTO(Integer cid, Integer rootCid) {
		this.cid = cid;
		this.rootCid = rootCid;
	}
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getRootCid() {
		return rootCid;
	}
	public void setRootCid(Integer rootCid) {
		this.rootCid = rootCid;
	}
}
