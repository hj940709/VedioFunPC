package com.hj;

import java.io.Serializable;
import java.util.Map;

public class Vedio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name1;
	private String name2;
	private String season;
	private String episode;
	private String nextdate;
	private String refresh;
	private String freq;
	private String detail;
	private String version;
	public Vedio(Map<String,String> vedio){
		this.id = vedio.get("id");
		this.name1 = vedio.get("name1");
		this.name2 = vedio.get("name2");
		this.season = vedio.get("season");
		this.episode = vedio.get("episode");
		this.nextdate = vedio.get("nextdate");
		this.refresh = vedio.get("refresh");
		this.freq = vedio.get("freq");
		this.detail = vedio.get("detail");
		this.version = vedio.get("version");
	}
	public Vedio(String id,String name1,String name2,
			String season,String episode,String nextdate,
			String refresh,String freq,String detail,String version){
		this.id = id;
		this.name1 = name1;
		this.name2 = name2;
		this.season = season;
		this.episode = episode;
		this.nextdate = nextdate;
		this.refresh = refresh;
		this.freq = freq;
		this.detail = detail;
		this.version = version;
	}
	public static String[] getItemList(){
		return new String[]{"name1","name2","season","episode"
				,"nextdate","refresh","freq","detail","version"};
	}
	public static String[] getFullItemList(){
		return new String[]{"id","name1","name2","season","episode"
				,"nextdate","refresh","freq","detail","version"};
	}
	public String[] getParameterList(){
		return new String[]{this.name1,this.name2,this.season,this.episode,
				this.nextdate,this.refresh,this.freq,this.detail,this.version,this.id};
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getEpisode() {
		return episode;
	}
	public void setEpisode(String episode) {
		this.episode = episode;
	}
	public String getNextdate() {
		return nextdate;
	}
	public void setNextdate(String nextdate) {
		this.nextdate = nextdate;
	}
	public String getRefresh() {
		return refresh;
	}
	public void setRefresh(String refresh) {
		this.refresh = refresh;
	}
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
