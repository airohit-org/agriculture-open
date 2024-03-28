import mqtt from "mqtt";

export const connectMqtt = (tentid, callBack) => {

  const CONFIG = {
    url: "",
    options: {
      clean: true,
      connectTimeout: 4000,
      reconnectPeriod: 1000,
      clientId: `mqttx_${Math.random().toString(16).substr(2, 8)}`,
      username: "",
      password: "",
    },
    topic: `/tenant/${tentid}`,
  };

  const client = mqtt.connect(CONFIG.url, CONFIG.options);

  client.on("connect", () => {
    client.subscribe([CONFIG.topic], () => {
      console.log(`订阅了主题 ${CONFIG.topic}`);
    });
  });


  client.on("message", function (topic, message) {
    try {
      if (topic === CONFIG.topic) {
        const data = JSON.parse(message.toString());
        callBack?.(data);
      }
    } catch (error) {
        console.log(error);
    }
  });

  client.on("close", function () {
    console.log("已断开连接");
  });
};
