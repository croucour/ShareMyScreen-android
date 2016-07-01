//package sharemyscreen.sharemyscreen.Room;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import sharemyscreen.sharemyscreen.DAO.RoomsManager;
//import sharemyscreen.sharemyscreen.Entities.RoomEntity;
//import sharemyscreen.sharemyscreen.R;
//
//class MyAdapter extends BaseAdapter {
//
//    private final RoomsManager _roomsManager;
//    private List<RoomEntity> _roomEntityList;
//    private String _profile__id = null;
//
//    public void set_roomEntityList(List<RoomEntity> roomEntityList) {
//        if (roomEntityList == null) {
//            roomEntityList = this.updateRoomEntityList();
//        }
//        this._roomEntityList = roomEntityList;
//        this.notifyDataSetChanged();
//    }
//
//    private List<RoomEntity> updateRoomEntityList() {
//        return _profile__id != null ? this._roomsManager.selectAllByProfile_id(_profile__id) : null;
//    }
//
//    public MyAdapter(RoomsManager roomsManager, String profile__id) {
//        this._profile__id = profile__id;
//        this._roomsManager = roomsManager;
//        this._roomEntityList = updateRoomEntityList();
//    }
//
//    @Override
//    public int getCount() {
//        return this._roomEntityList == null ? 0 : this._roomEntityList.size();
//    }
//
//    @Override
//    public RoomEntity getItem(int position) {
//        return this._roomEntityList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = View.inflate(parent.getContext(), R.layout.single_row, null);
//            new ViewHolder(convertView);
//        }
//
//        ViewHolder holder = (ViewHolder) convertView.getTag();
//        RoomEntity room = getItem(position);
//        holder.tv_name.setText(room.get_name());
//        return convertView;
//    }
//
//    public void set_roomEntity(RoomEntity roomEntity) {
//        if (roomEntity != null) {
//            if (this._roomEntityList == null) {
//                this._roomEntityList = new ArrayList<>();
//            }
//            this._roomEntityList.add(roomEntity);
//        }
//        else {
//            this.set_roomEntityList(null);
//        }
//        this.notifyDataSetChanged();
//    }
//
//    public void delete(RoomEntity item) {
//        this._roomEntityList.remove(item);
//        this.notifyDataSetChanged();
//    }
//
//    public void add(RoomEntity item) {
//        this.set_roomEntity(item);
//    }
//
//    class ViewHolder {
//        ImageView iv_icon;
//        TextView tv_name;
//
//        public ViewHolder(View view) {
//            iv_icon = (ImageView) view.findViewById(R.id.image);
//            tv_name = (TextView) view.findViewById(R.id.title);
//            view.setTag(this);
//        }
//    }
//
//}