package com.inslab.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Zone의 CRUD를 수행할 구현 클래스
 */
@Repository
public class ZoneDaoService implements ZoneDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public ArrayList<Zone> getZones() {
		/*
		ArrayList<Record> result = new ArrayList<Record>();
        //sqlSession을 통하여 매핑한다.
		RecordMapper memberMapper = sqlSession.getMapper(RecordMapper.class);
        //getMember()의 메소드명과 mapper.mxl과 id는 동일해야한다.
        result = memberMapper.getRecords();
        */
		ArrayList<Zone> result = new ArrayList<Zone>();
		ZoneMapper zoneMapper = sqlSession.getMapper(ZoneMapper.class);
		result = zoneMapper.getZones();
		
		return result;
	}
	
	@Override
	public ArrayList<Zone> getZone(Zone _zone) {
		ZoneMapper zoneMapper = sqlSession.getMapper(ZoneMapper.class);
		return zoneMapper.getZone(_zone);
	}
	

	@Override
	public void insertZone(Zone _zone) {
		ZoneMapper zoneMapper = sqlSession.getMapper(ZoneMapper.class);
		zoneMapper.insertZone(_zone);
	}

	@Override
	public void updateZone(Zone _zone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteZone(Zone _zone) {
		// TODO Auto-generated method stub
		
	}
}
