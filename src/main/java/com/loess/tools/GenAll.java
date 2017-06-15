package com.loess.tools;

import com.loess.tools.gen.*;

/**
 * @author xurong
 */
public class GenAll {
    public static void main(String[] args) throws Exception {

		/*need to generate code ' tables name */
        String[] tables = new String[]{
               /* "orders"*/
        };

        GenModel.start(tables);
        GenAdv.start(tables);
        GenMapper.start(tables);
        GenCRUD.start(tables);
        Logger.print(tables);
    }
}
