package com.dl.mybaseproject.demo6;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.dl.common.base.BaseFragment;
import com.dl.common.manager.EditTextManager;
import com.dl.common.utils.DialogUtil;
import com.dl.common.utils.ToastUtil;
import com.dl.common.widget.ReboundScrollView;
import com.dl.common.widget.dialog.DialogNormal;
import com.dl.mybaseproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.bingoogolapple.androidcommon.adapter.BGADivider;

/**
 * created by dalang at 2018/11/21
 */
public class Demo6Fragment extends BaseFragment {
    @BindView(R.id.data_list)
    RecyclerView dataList;
    @BindView(R.id.scrollView)
    ReboundScrollView scrollView;
    @BindView(R.id.et_search)
    EditText etSearch;
    Unbinder unbinder;


    private List<Demo6Bean> data;
    private Demo6Adapter demo6Adapter;
    static List<String> urls = new ArrayList<>();
    static {
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949743847&di=5f83055d1cf7fbc1850bfd5a29da05db&imgtype=0&src=http%3A%2F%2Fwww.ghost64.com%2Fqqtupian%2FzixunImg%2Flocal%2F2017%2F10%2F10%2F15076281898186.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949646997&di=204bcfdf940d85a576f89c71f6257403&imgtype=0&src=http%3A%2F%2Fp2.gexing.com%2FG1%2FM00%2F31%2FB1%2FrBACE1H470aSbln7AAAXnSARkq4258_200x200_3.jpg%3Frecache%3D20131108");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949702931&di=f8dad75fa766242be1cf85006cc65df6&imgtype=0&src=http%3A%2F%2Fm.vstou.com%2Fimg%2F201510%2Fzkk8.jpg");
        urls.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3154479610,481282731&fm=26&gp=0.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949771236&di=e489782e2510dd8d9288e365cce134d4&imgtype=0&src=http%3A%2F%2Fpic33.photophoto.cn%2F20141008%2F0010023370892683_b.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949771234&di=106c283bf910dff789536d8e3eec8cdd&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D5e7e97d3f4d3572c66e29cd4ba126352%2F232dd42a2834349b5794aebfcfea15ce36d3be7e.jpg");
        urls.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2033833625,2976461360&fm=26&gp=0.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949743847&di=5f83055d1cf7fbc1850bfd5a29da05db&imgtype=0&src=http%3A%2F%2Fwww.ghost64.com%2Fqqtupian%2FzixunImg%2Flocal%2F2017%2F10%2F10%2F15076281898186.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949646997&di=204bcfdf940d85a576f89c71f6257403&imgtype=0&src=http%3A%2F%2Fp2.gexing.com%2FG1%2FM00%2F31%2FB1%2FrBACE1H470aSbln7AAAXnSARkq4258_200x200_3.jpg%3Frecache%3D20131108");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949702931&di=f8dad75fa766242be1cf85006cc65df6&imgtype=0&src=http%3A%2F%2Fm.vstou.com%2Fimg%2F201510%2Fzkk8.jpg");
        urls.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3154479610,481282731&fm=26&gp=0.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949771236&di=e489782e2510dd8d9288e365cce134d4&imgtype=0&src=http%3A%2F%2Fpic33.photophoto.cn%2F20141008%2F0010023370892683_b.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949771234&di=106c283bf910dff789536d8e3eec8cdd&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D5e7e97d3f4d3572c66e29cd4ba126352%2F232dd42a2834349b5794aebfcfea15ce36d3be7e.jpg");
        urls.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2033833625,2976461360&fm=26&gp=0.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542949812591&di=5b6677958277b4ce40d0e83d24370185&imgtype=0&src=http%3A%2F%2Fwenwen.soso.com%2Fp%2F20110417%2F20110417174547-776658219.jpg");
    }

    @Override
    public int getLayoutRes() {
        return R.layout.demo6_fragment;
    }

    @Override
    public void initView() {
        EditTextManager.setInputRule(etSearch);
        initData();
        initList();
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            List<String> arrayUrls = new ArrayList<>();
            for (int j = 0; j <=i; j++) {
                arrayUrls.add(urls.get(j));
            }
            data.add(new Demo6Bean(i,arrayUrls));
        }
    }

    private void initList() {
        demo6Adapter = new Demo6Adapter(dataList);
        dataList.addItemDecoration(BGADivider.newShapeDivider().setSizeDp(1).setColor(color(R.color.grayDD), true));
        dataList.setNestedScrollingEnabled(false);
        dataList.setFocusable(false);
        dataList.setAdapter(demo6Adapter);
        demo6Adapter.setData(data);
        demo6Adapter.setOnItemChildClickListener((parent, childView, position) -> {
            switch (childView.getId()) {
                case R.id.layout:
                    ToastUtil.normal(data.get(position).getCount()+"");
                    break;
                case R.id.tv_delete:
                    showDelete(position);
                    break;
                case R.id.tv_up:

                    Demo6Bean temp = data.remove(position);
                    demo6Adapter.notifyItemRemoved(position);
                    demo6Adapter.addFirstItem(temp);
                    scrollView.smoothScrollTo(0, 0);
                    break;
            }
        });


    }

    private void showDelete(final int position) {
        final DialogNormal dialog = DialogUtil.buildDialogNormal(mActivity, "温馨提示", "确认删除该条目");
        dialog.setSureListener(v -> {
            ToastUtil.normal("删除" + position);
            data.remove(position);
            demo6Adapter.notifyItemRemoved(position);
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void managerArguments() {

    }


}
