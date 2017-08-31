package com.jingle.testrecycleview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.maning.mndialoglibrary.MProgressDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_activity";
    private RecyclerView mRecycleView;
    private ActionBar actionBar;
    List<Model> list = new ArrayList<>();
    RecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setActionBar();
    }

    private void initView() {
        int width = getResources().getDisplayMetrics().widthPixels;
        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(getApplicationContext(), list,width);
        mRecycleView.setAdapter(adapter);
        getData();

        // adapter.setOnItemClickListener(this);//将接口传递到数据产生的地方
       /* mRecycleView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                Log.e("出现", String.valueOf(v.getId()));
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Log.e("消失", String.valueOf(v.getId()));
            }
        });*/
    }

    private void getData() {
        MProgressDialog.Builder builder = new MProgressDialog.Builder(this).isCanceledOnTouchOutside(false).setOnDialogDismissListener(new MProgressDialog.OnDialogDismissListener() {
            @Override
            public void dismiss() {
                    ToastUtil.showToast(MainActivity.this,"加载完成！");
            }
        });
        //final MProgressDialog progressDialog = builder.isCanceledOnTouchOutside(true).setBackgroundViewColor(R.color.mycolor).setBackgroundWindowColor(R.color.aliceblue).build();
        final MProgressDialog progressDialog = builder.build();
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Document doc = Jsoup.connect("http://www.ttmeiju.com/summary.html").get();
                    Elements elements = doc.select(".ranktop3");
                    for (Element e : elements) {
                        String title = e.select("a").text();
                        String imageUrl = e.select("img").attr("src");
                        String url = e.select("a").attr("abs:href");
                        list.add(new Model(title, url, imageUrl));
                    }
                    Elements _elements = doc.select("td>a");
                    for (Element e : _elements
                            ) {
                        String title = e.text();
                        String indexUrl = e.attr("abs:href");
                        Document _doc = Jsoup.connect(indexUrl).get();
                        Elements elements_ = _doc.select("#spic");
                        String imageUrl = elements_.first().attr("src");
                        list.add(new Model(title, indexUrl, imageUrl));

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }
                    });
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(getApplicationContext(), "请求错误!");
                            Log.e(TAG, "请求错误");
                        }
                    });
                }

            }
        }).start();

    }

  /*  @Override
    public void onItemClick(int position, Model model) {
        ToastUtil.showToast(this, "点击了"+position);
    }*/

    private void setActionBar() {
        actionBar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            //显示标题
            actionBar.setDisplayShowTitleEnabled(true);
            //actionbar.setTitle(getString(R.string.app_name));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "退出", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar,menu);*/
        return super.onCreateOptionsMenu(menu);
    }

    public Bitmap getUrlImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setConnectTimeout(6000);
            client.setUseCaches(true);
            client.setDoInput(true);
            client.connect();
            InputStream is = client.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            Log.e(TAG, "图片解析解析错误");
        }
        return bitmap;

    }
}
