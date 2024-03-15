package com.example.doan_sale.Admin;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
public class AvatarAdapter extends BaseAdapter {
        private Context mContext;
        private int[] mAvatarIds;
        public AvatarAdapter(Context context, int[] avatarIds) {
            mContext = context;
            mAvatarIds = avatarIds;
        }
        @Override
        public int getCount() {
            return mAvatarIds.length;
        }
    @Override
    public Object getItem(int position) {
        return mAvatarIds[position];
    }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mAvatarIds[position]);
            return imageView;
        }
    }
