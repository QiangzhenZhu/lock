package com.hzdongcheng.parcellocker.views.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.parcellocker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationFragment extends DialogFragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    Unbinder unbinder;
    @BindView(R.id.view_bg)
    View viewBg;
    @BindView(R.id.line_horizontal)
    View lineHorizontal;
    @BindView(R.id.bt_positive)
    Button btPositive;
    @BindView(R.id.bt_negative)
    Button btNegative;

    private String title;
    private CharSequence message;

    private OnFragmentInteractionListener mListener;
    private CharSequence left = "";
    private CharSequence right = "";

    public InformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title   Parameter 1.
     * @param message Parameter 2.
     * @return A new instance of fragment InformationFragment.
     */
    public static InformationFragment newInstance(String title, CharSequence message) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putCharSequence(ARG_MESSAGE, message);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            message = getArguments().getCharSequence(ARG_MESSAGE);
        }
    }

    public void setButtonText(String left, String right) {
        this.left = left;
        this.right = right;
        if (getView() != null) {
            btNegative.setText(left);
            btPositive.setText(right);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        tvTitle.setText(title);
        tvTips.setText(message);
        if (StringUtils.isNotEmpty(left.toString())) {
            btNegative.setText(left);
            btPositive.setText(right);
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);
    }

    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view);
        }
    }

    /**
     * 添加对话框确认回调事件
     *
     * @param listener 确认按钮回调事件
     */
    public void setOnFragmentInteractionListener(OnFragmentInteractionListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_positive, R.id.bt_negative})
    public void onViewClicked(View view) {
        onButtonPressed(view);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(View view);
    }
}
