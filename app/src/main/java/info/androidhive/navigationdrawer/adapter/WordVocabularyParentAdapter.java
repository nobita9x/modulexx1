package info.androidhive.navigationdrawer.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.model.WordInfo;
import info.androidhive.navigationdrawer.utils.ImageUtils;

/**
 * Created by 8470p on 12/17/2016.
 */
public class WordVocabularyParentAdapter extends BaseExpandableListAdapter {
    private Context mContext;

    private List<WordInfo> mVocabularyHeaderList;  // header titles

    // child data in format of header title, child title
    private HashMap<WordInfo, List<WordInfo>> mVocabularyChildList;

    public WordVocabularyParentAdapter(Context context, List<WordInfo> listDataHeader,
                                       HashMap<WordInfo, List<WordInfo>> listChildData) {
        this.mContext = context;
        this.mVocabularyHeaderList = listDataHeader;
        this.mVocabularyChildList = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.mVocabularyChildList.get(this.mVocabularyHeaderList.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolder holder;
        WordInfo wordInfo = (WordInfo) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.word_vocabulary_item_child, null);

            holder = new ViewHolder();

            holder.eng_word = (TextView) convertView.findViewById(R.id.eng_word);
            holder.viet_word = (TextView) convertView.findViewById(R.id.viet_word);
            holder.iconView = (ImageView) convertView.findViewById(R.id.app_icon);
            holder.expandView = (ImageView) convertView.findViewById(R.id.nextView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null != wordInfo) {
            holder.eng_word.setText(wordInfo.getEnglsih());
            holder.viet_word.setText(wordInfo.getVietnamese());
            holder.iconView.setImageDrawable(wordInfo.getIcon());
            holder.iconView.setClipToOutline(true);
        }
        holder.expandView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.alarm_off));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mVocabularyChildList.get(this.mVocabularyHeaderList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mVocabularyHeaderList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mVocabularyHeaderList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        ViewHolder holder;
        WordInfo wordInfo = (WordInfo) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.word_vocabulary_item_parent, null);

            holder = new ViewHolder();

            holder.eng_word = (TextView) convertView.findViewById(R.id.eng_word);
            holder.viet_word = (TextView) convertView.findViewById(R.id.viet_word);
            holder.iconView = (ImageView) convertView.findViewById(R.id.app_icon);
            holder.expandView = (ImageView) convertView.findViewById(R.id.expand);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null != wordInfo) {
            holder.eng_word.setText(wordInfo.getEnglsih());
            holder.viet_word.setText(wordInfo.getVietnamese());
//            holder.iconView.setImageDrawable(wordInfo.getIcon());
            holder.iconView.setClipToOutline(true);
            Glide.with(mContext).load(Uri.parse(ImageUtils.loadDrawableChild(groupPosition))).centerCrop().into(holder.iconView);

            holder.expandView.setImageDrawable(wordInfo.getExpandIcon());
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewHolder {
        public TextView eng_word;
        public TextView viet_word;
        public ImageView iconView;
        public ImageView expandView;
    }
}