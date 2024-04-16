/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.wgllss.dynamic.runtime.library;
// Declare any non-default types here with import statements

public interface WXDynamicAidlInterface extends android.os.IInterface {
    /**
     * Default implementation for WXDynamicAidlInterface.
     */
    public static class Default implements com.wgllss.dynamic.runtime.library.WXDynamicAidlInterface {
        @Override
        public void onBind(java.lang.String serviceName, java.lang.String packageName, java.lang.String pluginApkPath) throws android.os.RemoteException {
        }

        @Override
        public void onUnbind(java.lang.String serviceName) throws android.os.RemoteException {
        }

        @Override
        public java.lang.String onAidlCallBack(java.lang.String serviceName, int methodID) throws android.os.RemoteException {
            return null;
        }

        @Override
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements com.wgllss.dynamic.runtime.library.WXDynamicAidlInterface {
        private static final java.lang.String DESCRIPTOR = "com.wgllss.dynamic.plugin.runtime.WXDynamicAidlInterface";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.wgllss.dynamic.plugin.runtime.WXDynamicAidlInterface interface,
         * generating a proxy if needed.
         */
        public static com.wgllss.dynamic.runtime.library.WXDynamicAidlInterface asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof com.wgllss.dynamic.runtime.library.WXDynamicAidlInterface))) {
                return ((com.wgllss.dynamic.runtime.library.WXDynamicAidlInterface) iin);
            }
            return new com.wgllss.dynamic.runtime.library.WXDynamicAidlInterface.Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            java.lang.String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_onBind: {
                    data.enforceInterface(descriptor);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    this.onBind(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_onUnbind: {
                    data.enforceInterface(descriptor);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    this.onUnbind(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_onAidlCallBack: {
                    data.enforceInterface(descriptor);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    int _arg1;
                    _arg1 = data.readInt();
                    java.lang.String _result = this.onAidlCallBack(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements com.wgllss.dynamic.runtime.library.WXDynamicAidlInterface {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public void onBind(java.lang.String serviceName, java.lang.String packageName, java.lang.String pluginApkPath) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(serviceName);
                    _data.writeString(packageName);
                    _data.writeString(pluginApkPath);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_onBind, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        getDefaultImpl().onBind(serviceName, packageName, pluginApkPath);
                        return;
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public void onUnbind(java.lang.String serviceName) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(serviceName);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_onUnbind, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        getDefaultImpl().onUnbind(serviceName);
                        return;
                    }
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public java.lang.String onAidlCallBack(java.lang.String serviceName, int methodID) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.lang.String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(serviceName);
                    _data.writeInt(methodID);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_onAidlCallBack, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().onAidlCallBack(serviceName, methodID);
                    }
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            public static com.wgllss.dynamic.runtime.library.WXDynamicAidlInterface sDefaultImpl;
        }

        static final int TRANSACTION_onBind = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_onUnbind = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_onAidlCallBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);

        public static boolean setDefaultImpl(com.wgllss.dynamic.runtime.library.WXDynamicAidlInterface impl) {
            // Only one user of this interface can use this function
            // at a time. This is a heuristic to detect if two different
            // users in the same process use this function.
            if (Stub.Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static com.wgllss.dynamic.runtime.library.WXDynamicAidlInterface getDefaultImpl() {
            return Stub.Proxy.sDefaultImpl;
        }
    }

    public void onBind(java.lang.String serviceName, java.lang.String packageName, java.lang.String pluginApkPath) throws android.os.RemoteException;

    public void onUnbind(java.lang.String serviceName) throws android.os.RemoteException;

    public java.lang.String onAidlCallBack(java.lang.String serviceName, int methodID) throws android.os.RemoteException;
}
