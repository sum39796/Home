package com.kantan.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.kantan.home.R;

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

        // View繧貞�蛻?�逕ｨ縺励※縺?�?�蝣?�蜷医?��?��縺溘�?View繧�?��懊ｉ縺?�縺?�
        if (view == null) {
            inflater =  (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_layout, null);
            TextView label = (TextView)view.findViewById(R.id.tv);
            holder = new ViewHolder();
            label.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"mplus-1c-thin.ttf"));
            holder.labelText = label;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 迚ｹ螳壹?�陦後�繝�?�繧?�繧貞叙蠕�
        String str = getItem(position);

        if (!TextUtils.isEmpty(str)) {
            // 繝�く繧?�繝医ン繝･繝ｼ縺?�繝ｩ繝吶Ν繧偵そ繝�ヨ
            holder.labelText.setText(str);
        }

        // 陦梧?�弱↓閭梧勹濶?�繧貞､峨∴繧?�
        if(position%3==0){
            holder.labelText.setBackgroundColor(Color.parseColor("#696969"));
        }else if(position%3==1){
            holder.labelText.setBackgroundColor(Color.parseColor("#696969"));
        }else{
            holder.labelText.setBackgroundColor(Color.parseColor("#696969"));
        }

        // XML縺?�螳夂ｾ?�縺励�?��?�繝九Γ繝ｼ繧?�繝ｧ繝ｳ繧定ｪ?�縺?�霎ｼ繧?�
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.item_motion);
        // 繝ｪ繧?�繝医�?��?�繝�Β縺?�繧?�繝九Γ繝ｼ繧?�繝ｧ繝ｳ繧帝幕蟋?�
        view.startAnimation(anim);

        return view;
    }
}
