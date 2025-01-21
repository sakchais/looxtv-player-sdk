package net.thaicom.sdk.looxtv;


import androidx.fragment.app.Fragment;
import androidx.leanback.app.BrowseSupportFragment;

/**
 * Created by tong on 27/11/2019 AD.
 */

public abstract class BaseFragment extends Fragment {
    protected abstract BrowseSupportFragment.MainFragmentAdapter getMainFragmentAdapter();

    protected boolean getFragmentHostValid(){
        if ((getMainFragmentAdapter() != null)
                && (getMainFragmentAdapter().getFragmentHost() != null)) {
            return true;
        }
        return false;
    }
}

