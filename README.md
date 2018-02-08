# Frecyclerview
简洁友好的 FRecyclerview



##  如何使用？

# How to use ?

#### Gradle

**Step 1.** Add the JitPack repository to your build file



	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}



**Step 2.** Add the dependency



	dependencies {
	        compile 'com.github.pangrongxian:FRecyclerView:1.0.3'
	}



**Step 3.** add it to your xml


    <com.prx.frecyclerview.FRecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent">
	</com.prx.frecyclerview.FRecyclerView>



###  For example :

### add a header And add a footer ——>

## 功能支持：添加头布局，尾布局 和 空布局
 		

	private void initView() {

        mRecyclerView = findViewById(R.id.recycler_view);
        emptyView = findViewById(R.id.empty_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        View headerView = LayoutInflater.from(this).inflate(R.layout.recycler_header, mRecyclerView, false);
        View footerView = LayoutInflater.from(this).inflate(R.layout.recycler_footer, mRecyclerView, false);

        adapter = new FRecyclerViewAdapter(MainActivity.this);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addHeaderView(headerView);
        mRecyclerView.addFooterView(footerView);

        /**
         * 当数据源List的size为0时候，空布局显示
         */
        mRecyclerView.setEmptyView(emptyView);
    }


![](http://ww1.sinaimg.cn/large/006rIajegy1fo90pr3d71j30k00zk74g.jpg)