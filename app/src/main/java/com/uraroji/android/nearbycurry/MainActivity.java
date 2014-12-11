package com.uraroji.android.nearbycurry;

import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    private static final String API_KEYWORD = "カレー";

    private static final int API_RANGE = 5; // 3000m

    private static final int UPDATE_LOCATION_INTERVAL_SEC = 30;

    private LocationRequest mLocationRequest;

    private GoogleApiClient mGoogleApiClient;

    private ListView mListView;

    private ShopListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);
        mListAdapter = new ShopListAdapter(this);
        mListView.setAdapter(mListAdapter);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        mLocationRequest = LocationRequest.create();
                        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        mLocationRequest.setInterval(UPDATE_LOCATION_INTERVAL_SEC * 1000);

                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                mGoogleApiClient, mLocationRequest, new LocationListener() {
                                    @Override
                                    public void onLocationChanged(Location location) {
                                        updateList(location);
                                    }
                                });
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.i(TAG, "GoogleApiClient connection has been suspend.");
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
                        Log.i(TAG, "GoogleApiClient connection has failed.");
                    }
                })
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void updateList(Location location) {
        if (location == null) {
            Log.v(TAG, "Location is empty.");
            return;
        }

        Log.v(TAG, "Current location: " + location.toString());

        ApiInterface api = ApiClientManager.create(ApiInterface.class);
        api.gourmet(BuildConfig.API_KEY, API_KEYWORD, location.getLatitude(), location.getLongitude(), API_RANGE, new Callback<ApiGourmetResponse>() {
            @Override
            public void success(final ApiGourmetResponse apiGourmetResponse, Response response) {
                mListAdapter.setShop(apiGourmetResponse.getResults().getShop());
                mListAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast.makeText(MainActivity.this, R.string.no_shop_available, Toast.LENGTH_LONG).show();
                Log.i(TAG, "Failed to API access.");
            }
        });
    }

}
