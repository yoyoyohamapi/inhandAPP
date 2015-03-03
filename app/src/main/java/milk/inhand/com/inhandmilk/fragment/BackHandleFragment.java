package milk.inhand.com.inhandmilk.fragment;

import android.app.Fragment;
import android.os.Bundle;

/**
 * BackHandleFragment
 * Desc:支持返回键监听的fragment
 * Team: InHand
 * User:Wooxxx
 * Date: 2015-03-03
 * Time: 12:43
 */
public abstract class BackHandleFragment extends Fragment {
    /**
     * BackHandledInterface
     * 该接口用于告知宿主Activity当前fragment
     */
    public interface BackHandledInterface{
        public abstract void setSelectedFragment(BackHandleFragment selectedFragment);
    }

    protected  BackHandledInterface mBackHandledInterface;

    /**
     *子类Fragment重写onBackPressed()方法，完成自己的返回键逻辑
     */
    public abstract boolean onBackPressed();

    /**
     *创建阶段，初始化BackHandledInterface对象
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(getActivity() instanceof  BackHandledInterface)){
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        }else{
            this.mBackHandledInterface = (BackHandledInterface)getActivity();
        }
    }

    /**
     * Fragment真正开始阶段，告知宿主Activity当前自己正在显示
     */
    @Override
    public void onStart() {
        super.onStart();
        mBackHandledInterface.setSelectedFragment(this);
    }
}
