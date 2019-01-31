package com.inslab.mybatis;

import java.util.ArrayList;

/**
 * Record의 CRUD를 위한 인터페이스
 */
public interface RecordDao {

	public ArrayList<Record> getRecords();
	
	public ArrayList<Record> getRecord(Record _record);
	public ArrayList<Record> getRecordForId(Record _record);
	
	public void insertRecord(Record _record);
	public void updateRecord(Record _record);
	public void deleteRecord(Record _record);
}
