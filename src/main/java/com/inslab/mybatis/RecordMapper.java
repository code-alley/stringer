package com.inslab.mybatis;

import java.util.ArrayList;

/**
 * DB Mapper와 연결될 인터페이스
 */
public interface RecordMapper {

	public ArrayList<Record> getRecords();
	
	public ArrayList<Record> getRecord(Record _record);
	public ArrayList<Record> getRecordForId(Record _record);
	
	public void insertRecord(Record _record);
	public void updateRecord(Record _record);
	public void deleteRecord(Record _record);
}
