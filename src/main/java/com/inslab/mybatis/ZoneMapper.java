package com.inslab.mybatis;

import java.util.ArrayList;

/**
 * DB Mapper와 연결될 인터페이스
 */
public interface ZoneMapper {

	public ArrayList<Zone> getZones();
	public ArrayList<Zone> getZone(Zone _zone);
	public void insertZone(Zone _zone);
	public void updateZone(Zone _zone);
	public void deleteZone(Zone _zone);
}
