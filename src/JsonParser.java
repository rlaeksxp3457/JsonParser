import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser {

        // 국가
        enum LocalizeType {
            KR, EN
        }

        // 변환할 데이터구조
        enum LocalizeParseType {
            JSON
        }

        // 국가 주소 값
        private static Map<String, Map<String, String>> parent = new HashMap<>();
        public JsonParser(LocalizeParseType parseType) throws ParseException {

            if (parseType == LocalizeParseType.JSON) {
                ParseJson();
            }
        }

        private static void ParseJson() throws ParseException {
            String local = "{\r\n"
                    + "    \"KR\": [\r\n"
                    + "        {\r\n"
                    + "            \"key\": \"register\",\r\n"
                    + "            \"value\": \"회원가입\"\r\n"
                    + "        },\r\n"
                    + "        {\r\n"
                    + "            \"key\": \"login\",\r\n"
                    + "            \"value\": \"로그인\"\r\n"
                    + "        }\r\n"
                    + "    ]\r\n"
                    + "    \"EN\": [\r\n"
                    + "        {\r\n"
                    + "            \"key\": \"register\",\r\n"
                    + "            \"value\": \"register\"\r\n"
                    + "        },\r\n"
                    + "        {\r\n"
                    + "            \"key\": \"login\",\r\n"
                    + "            \"value\": \"login\"\r\n"
                    + "        }\r\n"
                    + "    ]\r\n"
                    + "}";

            JSONParser jsonParse = new JSONParser();

            //문자열 local 를 JSONObject 로 변환
            //local 의 키값이 KR(key)일때 value = index 0 = key: "register, value = 회원가입, index 1 = key: "login", value = "로그인",
            JSONObject obj =  (JSONObject)jsonParse.parse(local);

            //enum 값을 전부다 빼오는 foreach 문
            for (LocalizeType type : LocalizeType.values()) {
                //parent value 값 지정을 위함 map 태그
                Map<String, String> map = new HashMap<>();
                //구조상 KR 키값 안 value 는 배열이기 때문에 형변환!
                //obj2 안 배열은 type 이 KR,EN,JP일때 개속 바뀜
                JSONArray obj2 = (JSONArray) obj.get(type.toString());
                //테스트
                System.out.println(type.name() + obj2);
                for (Object o : obj2) {
                    //Array 안 배열의 구조를 Object 로 변경 java map<key,value> 와 같은 구조
                    JSONObject jsonObject = (JSONObject) o;
                    //i번째 인덱스에 존재하는 배열을 Object 로 형변환 후 java map 컬렉션에 대입
                    map.put(jsonObject.get("key").toString(), jsonObject.get("value").toString());
                }
                //테스트
                System.out.printf( "key:%s , value:%s","register", map.get("register") + "\n");
                System.out.printf( "key:%s , value:%s","login", map.get("login") + "\n");


                //JSON 형태 문자열을 파싱하여 map<String,map<String,String>> 에 대입하는 ParseJson2() 완성.
                parent.put(type.toString(), map);
            }
        }
        public static void main(String[] args) throws ParseException {
            new JsonParser(LocalizeParseType.JSON);
        }
    }

