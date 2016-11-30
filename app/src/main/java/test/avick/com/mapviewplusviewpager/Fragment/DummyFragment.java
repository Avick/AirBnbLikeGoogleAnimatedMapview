package test.avick.com.mapviewplusviewpager.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.avick.com.mapviewplusviewpager.R;

/**
 * Created by avick on 11/30/16.
 */

public class DummyFragment extends Fragment {

    public static final String PAGE_LABEL = "page_no";
    int position;
    LinearLayout selectorLayout;
    TextView txtPage;

    public static DummyFragment newInstance(int position) {
        DummyFragment frag = new DummyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_LABEL, position);
        frag.setArguments(bundle);
        return frag;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            position = getArguments().getInt(PAGE_LABEL);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dummy_fragment_layout, container, false);
        selectorLayout = (LinearLayout) view.findViewById(R.id.layout_selected_marker);
        txtPage = (TextView) view.findViewById(R.id.txt_page_no);
        txtPage.setText(""+(position+1));
        if(position == 0) {
            makeFragmentSelected();
        } else {
            removeFragmentSelected();
        }

        return view;
    }

    public void makeFragmentSelected() {
        selectorLayout.setVisibility(View.VISIBLE);
    }

    public void removeFragmentSelected() {
        selectorLayout.setVisibility(View.INVISIBLE);
    }
}
