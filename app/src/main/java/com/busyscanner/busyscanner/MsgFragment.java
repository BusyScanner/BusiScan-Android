package com.busyscanner.busyscanner;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MsgFragment extends Fragment {
    protected ProgressBar progressBar;
    protected TextView msgView;
    private Integer textStyleId;

    protected static class State {
        int busy;
        List<String> msgs;
    }
    protected State state = new State();

    public MsgFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean isBusy() {
        return state.busy > 0;
    }

    public boolean hasMsg() {
        return state.msgs != null && !state.msgs.isEmpty();
    }

    private String getMessage() {
        return hasMsg() ? TextUtils.join("\n\n", state.msgs) : "";
    }

    protected void applyState() {
        if (getView() != null) {
            progressBar.setVisibility(isBusy() ? View.VISIBLE : View.GONE);
            msgView.setVisibility(hasMsg() ? View.VISIBLE : View.GONE);
            msgView.setText(getMessage());
            final FragmentTransaction fm = getChildFragmentManager().beginTransaction();
            if (isBusy() || hasMsg()) {
                fm.show(this);
            } else {
                fm.hide(this);
            }
            fm.commitAllowingStateLoss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.android_msg_fragment, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.msgfragment_progressBar);
        msgView = (TextView) view.findViewById(R.id.msgfragment_msg);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        applyState();
    }

    /**
     * Increment the busy state. Will cause progress bar to be displayed.
     */
    public void pushBusy() {
        ++state.busy;
        applyState();
    }

    /**
     * Decrement the progress bar busy state. When the progress bar busy state reaches zero, the
     * progress bar will be hidden.
     */
    public void popBusy() {
        if (state.busy > 0) {
            --state.busy;
            applyState();
        }
    }

    /**
     * Pop all busy.
     */
    public void clearBusy() {
        state.busy = 0;
        applyState();
    }

    /**
     * Clear the message for the fragment. The message text view is made hidden on clearing the message.
     */
    public void clearMsg() {
        state.msgs = null;
        applyState();
    }

    /**
     * Add the given message to current messages, if any.
     * @param msg
     */
    public void appendMsg(final String msg) {
        if (msg != null && !msg.trim().isEmpty()) {
            if (state.msgs == null) {
                state.msgs = new ArrayList<>();
            }
            state.msgs.add(msg);
            applyState();
        }
    }

    /**
     * Set the message text for the fragment. The message text and fragment is automatically made
     * visible if the message contain text.
     * @param msg
     */
    public void setMsg(final String msg) {
        state.msgs = null;
        if (msg != null && !msg.trim().isEmpty()) {
            state.msgs = new ArrayList<>();
            state.msgs.add(msg);
        }
        applyState();
    }

    public void setMsg(final Context context, final int msgResourceId) {
        setMsg(context.getString(msgResourceId));
    }
}

