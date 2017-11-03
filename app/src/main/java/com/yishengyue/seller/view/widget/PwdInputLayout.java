package com.yishengyue.seller.view.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yishengyue.seller.R;

import java.util.ArrayList;


public class PwdInputLayout extends RelativeLayout {

    public EditText getEditText() {
        return editText;
    }

    private EditText editText;
    private TextView[] mTextViews;//使用一个数组存储密码框

    public ArrayList<String> getList() {
        return mList;
    }

    private ArrayList<String> mList = new ArrayList<>();//存储密码字符

    private int count;
    private InputCompleteListener inputCompleteListener;

    public PwdInputLayout(Context context) {
        this(context,null);
    }

    public PwdInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PwdInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextViews = new TextView[12];
        View view = View.inflate(context, R.layout.mall_pwd_layout,this);
        editText = (EditText) findViewById(R.id.item_edittext);
        mTextViews[0] = (TextView)view. findViewById(R.id.passwordframeView0);
        mTextViews[1] = (TextView)view. findViewById(R.id.passwordframeView1);
        mTextViews[2] = (TextView)view. findViewById(R.id.passwordframeView2);
        mTextViews[3] = (TextView)view. findViewById(R.id.passwordframeView3);
        mTextViews[4] = (TextView)view. findViewById(R.id.passwordframeView4);
        mTextViews[5] = (TextView)view. findViewById(R.id.passwordframeView5);
        mTextViews[6] = (TextView)view. findViewById(R.id.passwordframeView6);
        mTextViews[7] = (TextView)view. findViewById(R.id.passwordframeView7);
        mTextViews[8] = (TextView)view. findViewById(R.id.passwordframeView8);
        mTextViews[9] = (TextView)view. findViewById(R.id.passwordframeView9);
        mTextViews[10] = (TextView)view. findViewById(R.id.passwordframeView10);
        mTextViews[11] = (TextView)view. findViewById(R.id.passwordframeView11);
        editText.setCursorVisible(false);//将光标隐藏
        setListener();
    }

    private void setListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().equals("")) {
                    if (count < 12)
                        mList.add(editable.toString());
                    editText.setText("");
                    reset();
                }

            }
        });

        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL  && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (count > 0){
                        mList.remove(count - 1);
                        reset();
                    }
                    return true;
                }
                return false;
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (count == mTextViews.length){
                        StringBuffer mS = new StringBuffer();
                        for (int i = 0; i < count; i++){
                            mS.append(mList.get(i));
                        }
                        if (inputCompleteListener != null)
                            inputCompleteListener.inputComplete(mS.toString());
                    }else {
                        Toast.makeText(getContext(),"请输入12位验证码",Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    private void  reset(){
        count = mList.size();
        for (int i = 0; i < mTextViews.length;i++){
            mTextViews[i].setText("");
            mTextViews[i].setBackgroundResource(R.drawable.mine_login_verification_border_false);
        }
        for (int i = 0; i < count; i++){
            mTextViews[i].setText(mList.get(i));
            mTextViews[i].setBackgroundResource(R.drawable.mine_login_verification_border_true);
        }
        if (count < mTextViews.length)
            mTextViews[count].setText("丨");

        if (count == mTextViews.length){
            StringBuffer mS = new StringBuffer();
            for (int i = 0; i < count; i++){
                mS.append(mList.get(i));
            }
            if (inputCompleteListener != null)
                inputCompleteListener.inputComplete(mS.toString());
        }
    }

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener{
        void inputComplete(String string);
    }

    public void clear(){
        mList.clear();
        reset();
    }

}
