class Conta {

  private float saldo;

  public Conta() {
    saldo = 0f;
  }

  public Conta(float inicial) {
    saldo = inicial;
  }

  public float getSaldo() {
    return saldo;
  }

  // public void deposita(float valor) { // errado  
  public synchronized void deposita(float valor) {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    saldo = saldo + valor;
  }

  // public void retira(float valor) { // errado
  public synchronized void retira(float valor) {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    saldo = saldo - valor;
  }

}

class ThreadDeposita extends Thread {

  private Conta c;
  private int n;
  private float val;

  ThreadDeposita(Conta c, int n, float val) {
    this.c = c;
    this.n = n;
    this.val = val;
  }
  float getTotal() {
    return this.n * this.val;
  }
  public void run() {
    for (int i = 0; i < this.n; i++) { // faz n depositos de val
      c.deposita(this.val);
    }
  }

}

class ThreadRetira extends Thread {

  private Conta c;
  private int n;
  private float val;

  ThreadRetira(Conta c, int n, float val) {
    this.c = c;
    this.n = n;
    this.val = val;
  }
  float getTotal() {
    return this.n * this.val;
  }
  public void run() {
    for (int i = 0; i < this.n; i++) { // faz n retiradas de val
      c.retira(this.val);
    }
  }

}

class OperacoesBancarias {

  public static void main(String[] args) {

    float saldoInicial = 100f;
    Conta c = new Conta(saldoInicial);

    ThreadDeposita td = new ThreadDeposita(c, 10, 100f);
    ThreadRetira tr = new ThreadRetira(c, 5, 50f);

    td.start();
    tr.start();
    try {
      td.join();
      tr.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Saldo da conta: " + c.getSaldo());
    System.out.println("Saldo deveria ser: " + (saldoInicial + td.getTotal() - tr.getTotal()));
  }

}
