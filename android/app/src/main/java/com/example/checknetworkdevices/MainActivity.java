package com.example.checknetworkdevices;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import com.stealthcopter.networktools.SubnetDevices;
import com.stealthcopter.networktools.subnet.Device;

import java.net.InetAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    TextView tvWifiState;
    TextView tvScanning, tvResult;

    ArrayList<InetAddress> inetAddresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWifiState = (TextView)findViewById(R.id.WifiState);
        tvScanning = (TextView)findViewById(R.id.Scanning);
        tvResult = (TextView)findViewById(R.id.Result);

        WifiManager wifiManager =
                (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        tvWifiState.setText(readtvWifiState(wifiManager));

        findSubnetDevices();
    }

    private void findSubnetDevices() {

        final long startTimeMillis = System.currentTimeMillis();
        tvScanning.setText("Scanning...");
        SubnetDevices subnetDevices = SubnetDevices.fromLocalAddress().findDevices(new SubnetDevices.OnSubnetDeviceFound() {

            @Override
            public void onDeviceFound(Device device) {

            }

            public void onFinished(ArrayList<Device> devicesFound) {
                float timeTaken =  (System.currentTimeMillis() - startTimeMillis)/1000.0f;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvScanning.setText("Scan Finished");
                        for(Device device:devicesFound) {
                            tvResult.append("Device " + device.hostname+ "\n");
                            tvResult.append("IP : " + device.ip + "\n");
                            tvResult.append("Dirección MAC : " + device.mac + "\n");
                            tvResult.append("\n");
                        }
                    }
                });

            }
        });
    }


    private String readtvWifiState(WifiManager wm){
        String result = "";
        switch (wm.getWifiState()){
            case WifiManager.WIFI_STATE_DISABLED:
                result = "WIFI_STATE_DISABLED";
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                result = "WIFI_STATE_DISABLING";
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                result = "WIFI_STATE_ENABLED";
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                result = "WIFI_STATE_ENABLING";
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                result = "WIFI_STATE_UNKNOWN";
                break;
            default:
        }
        return result;
    }
}