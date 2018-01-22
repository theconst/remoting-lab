package ua.kpi.apeps.repository;

import ua.kpi.apeps.model.EmployeeShiftRecord;

public interface EmployeeShiftRecordRepository extends Repository<EmployeeShiftRecord, Integer> {

    public Iterable<EmployeeShiftRecord> getAllEmployeeShiftRecords(Integer empId, Integer journalId);
}
