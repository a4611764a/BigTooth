package com.zb.bittooth.mine;

import java.util.HashMap;
import java.util.Map;
import com.mechat.mechatlibrary.MCClient;
import com.mechat.mechatlibrary.MCOnlineConfig;
import com.mechat.mechatlibrary.MCUserConfig;
import android.app.Activity;
import android.os.Bundle;

public class Service extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MCOnlineConfig onlineConfig = new MCOnlineConfig();
		// onlineConfig.setChannel("channel"); // 设置渠道

		// onlineConfig.setSpecifyAgent("4840", false); // 设置指定客服
		// onlineConfig.setSpecifyGroup("1"); // 设置指定分组
		// 更新用户信息，可选.
		// 详细信息可以到文档中查看：https://meiqia.com/docs/sdk/android.html
		MCUserConfig mcUserConfig = new MCUserConfig();
		Map<String, String> userInfo = new HashMap<String, String>();
		userInfo.put(MCUserConfig.PersonalInfo.REAL_NAME, "zb");
		userInfo.put(MCUserConfig.Contact.TEL, "13520844156");
		Map<String, String> userInfoExtra = new HashMap<String, String>();
		userInfoExtra.put("extra_key", "extra_value");
		userInfoExtra.put("gold", "10000");
		mcUserConfig.setUserInfo(this, userInfo, userInfoExtra, null);
		// 启动客服对话界面
		MCClient.getInstance().startMCConversationActivity(onlineConfig);
	}
}
