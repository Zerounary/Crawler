import groovy.sql.Sql

/**
 * Created by Zerounary on 2019/6/4.
 */
def url = 'jdbc:oracle:thin:@192.168.5.5:1521:testorcl'
def user = 'bosnds3'
def password = 'abc123'
def driver = 'oracle.jdbc.OracleDriver'
def sql =  Sql.newInstance(url, user, password, driver);
def memo = "";
K3 k3 = new K3(10)
def rst;
def no = 0;
sql.eachRow("SELECT id, openresult FROM test_lottery_his order by id"){ row ->
    rst =  k3.nextStep(row.openresult)
    if(rst.isBet){
        memo = "${rst.betResult}, ${rst.betAmt}倍".toString()
    }else{
        memo = "不下注"
    }
    sql.executeUpdate("""
      UPDATE test_lottery_his
      SET memo = ${memo}
      WHERE id = ${row.id + 1}
    """)
    println no++
}
sql.close()