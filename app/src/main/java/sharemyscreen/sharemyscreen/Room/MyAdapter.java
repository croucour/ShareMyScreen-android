package sharemyscreen.sharemyscreen.Room;

import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sharemyscreen.sharemyscreen.DAO.RoomsManager;
import sharemyscreen.sharemyscreen.Entities.Room;
import sharemyscreen.sharemyscreen.R;

//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RowViewHolder> {
//    private final RoomsManager _roomsManager;
//    private List<Room> _rooms;
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public TextView mTextView;
//        public ViewHolder(TextView v) {
//            super(v);
//            mTextView = v;
//        }
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public MyAdapter(RoomsManager roomsManager) {
//
//        this._roomsManager = roomsManager;
//        this._rooms = this._roomsManager.selectAll("id");
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public RowViewHolder onCreateViewHolder(ViewGroup parent,
//                                                   int viewType) {
//        // create a new view
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.single_row, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//
//        RowViewHolder vh = new RowViewHolder(v);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(RowViewHolder holder, int position) {
////        RowItems items = itemsList.get(position);
////        holder.textView.setText(String.valueOf(items.getTitle()));
////        holder.imageView.setBackgroundResource(items.getImgIcon());
//
//        Room room = this._rooms.get(position);
//        holder.textView.setText(room.get_name());
//    }
//
//    // Replace the contents of a view (invoked by the
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return this._rooms.size();
//    }
//
//
//    public class RowViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView imageView;
//        TextView textView;
//
//        public RowViewHolder(View view) {
//            super(view);
//            this.textView = (TextView) view.findViewById(R.id.title);
//            this.imageView = (ImageView) view.findViewById(R.id.image);
//        }
//    }
//
//    public class RowItems {
//        private String title;
//        private int imgIcon;
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public int getImgIcon() {
//            return imgIcon;
//        }
//
//        public void setImgIcon(int imgIcon) {
//            this.imgIcon = imgIcon;
//        }
//    }
//}

class MyAdapter extends BaseAdapter {

    private final RoomsManager _roomsManager;
    private List<Room> _rooms;

    public MyAdapter(RoomsManager roomsManager) {

        this._roomsManager = roomsManager;
        this._rooms = this._roomsManager.selectAll("id");
    }


    @Override
        public int getCount() {
            return this._rooms.size();
        }

        @Override
        public Room getItem(int position) {
            return this._rooms.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(),
                        R.layout.single_row, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            Room room = getItem(position);
//            holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
            holder.tv_name.setText(room.get_name());

            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.image);
                tv_name = (TextView) view.findViewById(R.id.title);
                view.setTag(this);
            }
        }

    }


