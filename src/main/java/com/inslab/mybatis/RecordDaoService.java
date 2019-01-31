package com.inslab.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//Service 클래스를 Repository로 등록함으로서 빈(bean) 클래스로 사용하능하게한다.
/**
 * Record CRUD를 수행하는 구현 클래스
 */
@Repository
public class RecordDaoService implements RecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public ArrayList<Record> getRecords() {
        
		RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
        return recordMapper.getRecords();
	}

	@Override
	public ArrayList<Record> getRecord(Record _record) {
		
		RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
        return recordMapper.getRecord(_record);
	}

	@Override
	public ArrayList<Record> getRecordForId(Record _record) {
		RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
        return recordMapper.getRecordForId(_record);
	}
	
	@Override
	public void insertRecord(Record _record) {
		RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
		recordMapper.insertRecord(_record);
	}

	@Override
	public void updateRecord(Record _record) {
		RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
		recordMapper.updateRecord(_record);
	}

	@Override
	public void deleteRecord(Record _record) {
		RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
		recordMapper.deleteRecord(_record);
		
	}
}
