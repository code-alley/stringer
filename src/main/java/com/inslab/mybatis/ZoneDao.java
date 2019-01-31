package com.inslab.mybatis;

import java.util.ArrayList;

/**
 * Zone의 CRUD를 위한 인터페이스
 */
public interface ZoneDao {

	public ArrayList<Zone> getZones();
	public ArrayList<Zone> getZone(Zone _zone);
	public void insertZone(Zone _zone);
	public void updateZone(Zone _zone);
	public void deleteZone(Zone _zone);
		
}
