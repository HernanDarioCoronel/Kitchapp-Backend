CREATE TABLE work_logs (
    id UUID PRIMARY KEY,
    employee_id UUID NOT NULL REFERENCES employees(id),
    type VARCHAR(20) NOT NULL
        CHECK (type IN ('CLOCK_IN','CLOCK_OUT','BREAK_START','BREAK_END','VACATION_START','VACATION_END')),
    timestamp TIMESTAMPTZ NOT NULL,
    notes VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_work_logs_employee_id ON work_logs(employee_id);
CREATE INDEX idx_work_logs_timestamp ON work_logs(timestamp DESC);
CREATE INDEX idx_work_logs_type ON work_logs(type);

-- No triggers or procedures needed; immutability enforced at application layer.
