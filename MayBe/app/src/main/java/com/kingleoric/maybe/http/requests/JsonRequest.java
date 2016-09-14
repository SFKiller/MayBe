package com.kingleoric.maybe.http.requests;

import com.kingleoric.maybe.http.base.Request;
import com.kingleoric.maybe.http.base.Response;

import org.json.JSONException;
import org.json.JSONObject;

/** For the response result is JSONObject
 * @author KingLeoric
 * @date 2016/8/5
 */
public class JsonRequest extends Request<JSONObject> {

    public JsonRequest(HttpMethod method, String requestUrl, RequestListener listener) {
        super(method, requestUrl, listener);
    }

    /**
     * Convert response into JSONObject
     * @param response Original result of request
     * @return
     */
    @Override
    public JSONObject parseResponse(Response response) {
        String jsonString = new String(response.getRawData());
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
