package com.itwray.study.thrid.dingtalk.frame.client;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkstorage_1_0.Client;
import com.aliyun.dingtalkstorage_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.itwray.study.thrid.dingtalk.frame.DingTalkAuthManager;
import com.itwray.study.thrid.dingtalk.frame.model.AbstractFileDto;
import com.itwray.study.thrid.dingtalk.frame.util.DingTalkApiClientUtil;
import com.itwray.study.thrid.dingtalk.frame.util.DingTalkFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 钉钉存储Client
 *
 * @author wangfarui
 * @since 2023/8/2
 */
@Slf4j
public class DingTalkStorageClient {

    public static CommitFileResponseBody.CommitFileResponseBodyDentry uploadFileDto(AbstractFileDto fileDto) {
        String uploadKey = getFileUploadInfo(fileDto);
        return commit(fileDto, uploadKey);
    }

    public static String getFileUploadInfo(AbstractFileDto fileDto) {
        Client storageClient = DingTalkApiClientUtil.createStorageClient();
        GetFileUploadInfoHeaders getFileUploadInfoHeaders = new GetFileUploadInfoHeaders();
        getFileUploadInfoHeaders.setXAcsDingtalkAccessToken(DingTalkAuthManager.getToken());
        GetFileUploadInfoRequest getFileUploadInfoRequest = new GetFileUploadInfoRequest()
                .setUnionId(DingTalkAuthManager.getDefaultUnionId())
                .setProtocol("HEADER_SIGNATURE")
                .setMultipart(false);

        try (InputStream is = DingTalkFileUtil.getInputStream(fileDto.getFileUrl())) {
            GetFileUploadInfoResponse fileUploadInfoWithOptions = storageClient.getFileUploadInfoWithOptions(DingTalkAuthManager.getSpaceId(), getFileUploadInfoRequest, getFileUploadInfoHeaders, new RuntimeOptions());
            //执行上传操作
            GetFileUploadInfoResponseBody body = fileUploadInfoWithOptions.getBody();
            URL url = new URL(body.getHeaderSignatureInfo().getResourceUrls().get(0));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Map<String, String> headers = body.getHeaderSignatureInfo().getHeaders();
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
                connection.setRequestProperty("Authorization", headers.get("Authorization"));
                connection.setRequestProperty("x-oss-date", headers.get("x-oss-date"));
            }
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setUseCaches(false);
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("contentType", "UTF-8");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.connect();
            OutputStream out = connection.getOutputStream();
            byte[] b = new byte[1024];
            int temp;
            while ((temp = is.read(b)) != -1) {
                out.write(b, 0, temp);
            }
            out.flush();
            out.close();
            int responseCode = connection.getResponseCode();
            connection.disconnect();
            if (responseCode == 200) {
                log.info("[DingTalkStorageClient]getFileUploadInfo: 上传成功");
            } else {
                log.info("[DingTalkStorageClient]getFileUploadInfo: 上传失败, responseCode: {}", responseCode);
            }
            log.info("[DingTalkStorageClient]getFileUploadInfo: {}", JSON.toJSONString(fileUploadInfoWithOptions));
            return body.getUploadKey();
        } catch (TeaException err) {
            throw err;
        } catch (Exception _err) {
            throw new TeaException(_err.getMessage(), _err);
        }
    }

    public static CommitFileResponseBody.CommitFileResponseBodyDentry commit(AbstractFileDto fileDto, String uploadKey) {
        CommitFileHeaders commitFileHeaders = new CommitFileHeaders();
        commitFileHeaders.setXAcsDingtalkAccessToken(DingTalkAuthManager.getToken());
        CommitFileRequest commitFileRequest = new CommitFileRequest()
                .setUnionId(DingTalkAuthManager.getDefaultUnionId())
                .setUploadKey(uploadKey)
                .setName(fileDto.getFileName())
                // 根目录
                .setParentId("0");
        String spaceId = DingTalkAuthManager.getSpaceId();
        Client storageClient = DingTalkApiClientUtil.createStorageClient();
        try {
            CommitFileResponse response = storageClient.commitFileWithOptions(spaceId, commitFileRequest, commitFileHeaders, new RuntimeOptions());
            log.info("[DingTalkStorageClient]commit: {}", JSON.toJSONString(response));
            return response.getBody().getDentry();
        } catch (TeaException err) {
            throw err;
        } catch (Exception _err) {
            throw new TeaException(_err.getMessage(), _err);
        }
    }

    public static GetFileDownloadInfoResponseBody.GetFileDownloadInfoResponseBodyHeaderSignatureInfo getFileDownloadInfo(String fileId) {
        Client client = DingTalkApiClientUtil.createStorageClient();
        GetFileDownloadInfoHeaders getFileDownloadInfoHeaders = new GetFileDownloadInfoHeaders();
        getFileDownloadInfoHeaders.setXAcsDingtalkAccessToken(DingTalkAuthManager.getToken());
        GetFileDownloadInfoRequest getFileDownloadInfoRequest = new GetFileDownloadInfoRequest()
                .setUnionId(DingTalkAuthManager.getDefaultUnionId());
        String spaceId = DingTalkAuthManager.getSpaceId();
        try {
            GetFileDownloadInfoResponse response = client.getFileDownloadInfoWithOptions(spaceId, fileId, getFileDownloadInfoRequest, getFileDownloadInfoHeaders, new RuntimeOptions());
            return response.getBody().getHeaderSignatureInfo();
        } catch (TeaException err) {
            if ("orgAuthLevelNotEnough".equals(err.getCode())) {
                // 忽略企业认证等级不足的异常
                log.error("获取文件下载信息失败", err);
                return null;
            } else {
                throw err;
            }
        } catch (Exception _err) {
            throw new TeaException(_err.getMessage(), _err);
        }
    }

}
