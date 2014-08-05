package org.openhab.binding.russound.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface RussoundConnection {

  public InputStream getInputStream();
  public OutputStream getOutputStream();
  public void connect() throws IOException;
  public void disconnect() throws IOException;
  
}
