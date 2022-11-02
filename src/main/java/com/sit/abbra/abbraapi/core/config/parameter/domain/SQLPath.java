package com.sit.abbra.abbraapi.core.config.parameter.domain;

import com.sit.abbra.abbraapi.sql.TestSQL;
import com.sit.abbra.abbraapi.sql.aboutus.AboutUsSQL;
import com.sit.abbra.abbraapi.sql.department.DepartmentSQL;
import com.sit.abbra.abbraapi.sql.employee.EmployeeSQL;
import com.sit.abbra.abbraapi.sql.ic.ICSQL;
import com.sit.abbra.abbraapi.sql.reference.ReferenceSQL;
import com.sit.abbra.abbraapi.sql.security.SecuritySQL;
import com.sit.abbra.abbraapi.sql.selectitem.SelectItemSQL;
import com.sit.abbra.abbraapi.sql.token.TokenSQL;
import com.sit.authen.Authentication;
import com.sit.core.common.domain.CommonSQLPath;

public enum SQLPath {

	TEST_SQL(TestSQL.class, "com/sit/abbra/abbraapi/sql/Test.sql"),
	LOGIN_SQL(SecuritySQL.class, "com/sit/abbra/abbraapi/sql/security/Login.sql"),
	LOG_SQL(SecuritySQL.class, "com/sit/abbra/abbraapi/sql/security/Log.sql"),
	SELECT_ITEM_SQL(SelectItemSQL.class, "com/sit/abbra/abbraapi/sql/selectitem/SelectItem.sql"),
	TOKEN_SQL(TokenSQL.class, "com/sit/abbra/abbraapi/sql/token/Token.sql"),
	AUTHEN_SQL(Authentication.class, "com/sit/authen/Authentication.sql"),
	
	HOME_SQL(TestSQL.class, "com/sit/abbra/abbraapi/sql/Home.sql"),
	ANNOUNCE_SQL(ICSQL.class, "com/sit/abbra/abbraapi/sql/ic/Announce.sql"),
	ACTIVITY_SQL(ICSQL.class, "com/sit/abbra/abbraapi/sql/ic/Activity.sql"),
	ABOUT_US_SQL(AboutUsSQL.class, "com/sit/abbra/abbraapi/sql/aboutus/AboutUs.sql"),
	DEPARTMENT_SQL(DepartmentSQL.class, "com/sit/abbra/abbraapi/sql/department/Department.sql"),
	EMPLOYEE_SQL(EmployeeSQL.class, "com/sit/abbra/abbraapi/sql/employee/Employee.sql"),
	REFERENCE_SQL(ReferenceSQL.class, "com/sit/abbra/abbraapi/sql/referenceWS/Reference.sql"),
	;
	
	private ReferenceSQLPath sqlPath;

	private SQLPath(Class<?> className, String path) {
		this.sqlPath = new ReferenceSQLPath(className, path);
	}

	public CommonSQLPath getSqlPath() {
		return sqlPath;
	}

	public String getPath() {
		return sqlPath.getPath();
	}
}
