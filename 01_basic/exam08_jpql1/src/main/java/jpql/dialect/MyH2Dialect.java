package jpql.dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * packageName : jpql.dialect
 * fileName : MyH2Dialect
 * author : haedoang
 * date : 2021/11/15
 * description :
 */
public class MyH2Dialect extends H2Dialect {

    public MyH2Dialect() {
        this.registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
