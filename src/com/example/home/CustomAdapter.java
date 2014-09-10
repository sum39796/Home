package com.example.home;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nakayamashohei on 2014/03/24.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    static class ViewHolder {
        TextView labelText;
    }

    private LayoutInflater inflater;

    public CustomAdapter(Context context,int textViewResourceId, ArrayList<String> labelList) {
        super(context,textViewResourceId, labelList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;

        // View繧貞�蛻ｩ逕ｨ縺励※縺�ｋ蝣ｴ蜷医�譁ｰ縺溘↓View繧剃ｽ懊ｉ縺ｪ縺�
        if (view == null) {
            inflater =  (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_layout, null);
            TextView label = (TextView)view.findViewById(R.id.tv);
            holder = new ViewHolder();
            holder.labelText = label;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 迚ｹ螳壹�陦後�繝��繧ｿ繧貞叙蠕�
        String str = getItem(position);

        if (!TextUtils.isEmpty(str)) {
            // 繝�く繧ｹ繝医ン繝･繝ｼ縺ｫ繝ｩ繝吶Ν繧偵そ繝�ヨ
            holder.labelText.setText(str);
        }

        // 陦梧ｯ弱↓閭梧勹濶ｲ繧貞､峨∴繧�
        if(position%3==0){
            holder.labelText.setBackgroundColor(Color.parseColor("#0000ff"));
        }else if(position%3==1){
            holder.labelText.setBackgroundColor(Color.parseColor("#1e90ff"));
        }else{
            holder.labelText.setBackgroundColor(Color.parseColor("#6495ed"));
        }

        // XML縺ｧ螳夂ｾｩ縺励◆繧｢繝九Γ繝ｼ繧ｷ繝ｧ繝ｳ繧定ｪｭ縺ｿ霎ｼ繧�
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.item_motion);
        // 繝ｪ繧ｹ繝医い繧､繝�Β縺ｮ繧｢繝九Γ繝ｼ繧ｷ繝ｧ繝ｳ繧帝幕蟋�
        view.startAnimation(anim);

        return view;
    }
}
