/**
 * 封装的网络请求方法
 * @author jimo
 * @type {{post: net.post}}
 */
let baseUrl = "http://localhost:8088";

const net = {
    /**
     * @param url
     * @param data  json格式
     * @param callback
     * @param errorHandle  可以不传
     */
    post(url, data, callback, errorHandle) {
        $.ajax({
            type: 'post',
            url: baseUrl + url,
            data: data,
            dataType: 'json', //返回数据形式为json10.0.0
            beforeSend: function (request) {
                request.setRequestHeader('Authentication', localStorage.getItem('TOKEN'));
            },
            success: function (result) {
                callback(result);
            },
            error: function (errorMsg) {
                if (errorHandle) {
                    errorHandle(errorMsg);
                } else {
                    console.log(errorMsg);
                }
            }
        })
    },
    /**
     * @param url
     * @param data  json格式
     * @param callback
     * @param errorHandle  可以不传
     */
    postJson(url, data, callback, errorHandle) {
        
        $.ajax({
            type: 'post',
            url: baseUrl + url,
            data: data,
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json', //返回数据形式为json10.0.0
            beforeSend: function (request) {
                request.setRequestHeader('Authentication', localStorage.getItem('TOKEN'));
            },
            success: function (result) {
                callback(result);
            },
            error: function (errorMsg) {
                if (errorHandle) {
                    errorHandle(errorMsg);
                } else {
                    console.log(errorMsg);
                }
            }
        })
    },
    get(url, data, callback, errorHandle) {
        $.ajax({
            type: 'GET',
            url: baseUrl + url,
            data: data,
            dataType: 'json', //返回数据形式为json10.0.0
            beforeSend: function (request) {
                request.setRequestHeader('Authentication', localStorage.getItem('TOKEN'));
            },
            success: function (result) {
                callback(result);
            },
            error: function (errorMsg) {
                if (errorHandle) {
                    errorHandle(errorMsg);
                } else {
                    console.log(errorMsg);
                }
            }
        })
    },
    /**
     * @param url
     * @param data  json格式
     * @param callback
     * @param errorHandle  可以不传
     */
    put(url, data, callback, errorHandle) {
        
        $.ajax({
            type: 'put',
            url: baseUrl + url,
            data: data,
            dataType: 'json', //返回数据形式为json10.0.0
            beforeSend: function (request) {
                request.setRequestHeader('Authentication', localStorage.getItem('TOKEN'));
            },
            success: function (result) {
                callback(result);
            },
            error: function (errorMsg) {
                if (errorHandle) {
                    errorHandle(errorMsg);
                } else {
                    console.log(errorMsg);
                }
            }
        })
    }
};
