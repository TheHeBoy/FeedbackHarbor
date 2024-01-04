package cn.hh.harbor.module.infra.dal.mysql.codegen;

import cn.hh.harbor.module.tool.dal.dataobject.codegen.SchemaColumnDO;
import cn.hh.harbor.module.tool.test.BaseDbUnitTest;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SchemaColumnMapperTest extends BaseDbUnitTest {

    @Resource
    private SchemaColumnMapper schemaColumnMapper;

    @Test
    public void testSelectListByTableName() {
        List<SchemaColumnDO> columns = schemaColumnMapper.selectListByTableName("", "inf_config");
        assertTrue(columns.size() > 0);
    }

}
