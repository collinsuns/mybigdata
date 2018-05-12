package cn.itcast.hive;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Project Name: hello-spark
 * User: Suns
 * Date: 2018-03-14 21:48
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class Lower extends UDF {
    public Text evaluate(final Text s){
        if(s==null){return null;}
        return new Text(s.toString().toLowerCase());
    }

}
