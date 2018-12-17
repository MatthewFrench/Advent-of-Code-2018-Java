public class Sample {
    int[] BeforeRegisterStates = new int[4];
    int[] AfterRegisterStates = new int[4];
    int OpCode;
    int A;
    int B;
    int C;

    public Sample(String s) {
        String[] lines = s.split("\n");
        String line0 = lines[0];
        line0 = line0.substring(line0.indexOf("[")+1);
        BeforeRegisterStates[0] = Integer.parseInt(line0.substring(0, 1));
        line0 = line0.substring(line0.indexOf(",")+2);
        BeforeRegisterStates[1] = Integer.parseInt(line0.substring(0, 1));
        line0 = line0.substring(line0.indexOf(",")+2);
        BeforeRegisterStates[2] = Integer.parseInt(line0.substring(0, 1));
        line0 = line0.substring(line0.indexOf(",")+2);
        BeforeRegisterStates[3] = Integer.parseInt(line0.substring(0, 1));
        String line1 = lines[1];
        String[] instruction = line1.split(" ");
        OpCode = Integer.parseInt(instruction[0]);
        A = Integer.parseInt(instruction[1]);
        B = Integer.parseInt(instruction[2]);
        C = Integer.parseInt(instruction[3]);
        String line2 = lines[2];
        line2 = line2.substring(line2.indexOf("[")+1);
        AfterRegisterStates[0] = Integer.parseInt(line2.substring(0, 1));
        line2 = line2.substring(line2.indexOf(",")+2);
        AfterRegisterStates[1] = Integer.parseInt(line2.substring(0, 1));
        line2 = line2.substring(line2.indexOf(",")+2);
        AfterRegisterStates[2] = Integer.parseInt(line2.substring(0, 1));
        line2 = line2.substring(line2.indexOf(",")+2);
        AfterRegisterStates[3] = Integer.parseInt(line2.substring(0, 1));
    }

    public Sample() {

    }

    public Sample(Sample s) {
        BeforeRegisterStates[0] = s.BeforeRegisterStates[0];
        BeforeRegisterStates[1] = s.BeforeRegisterStates[1];
        BeforeRegisterStates[2] = s.BeforeRegisterStates[2];
        BeforeRegisterStates[3] = s.BeforeRegisterStates[3];
        AfterRegisterStates[0] = s.AfterRegisterStates[0];
        AfterRegisterStates[1] = s.AfterRegisterStates[1];
        AfterRegisterStates[2] = s.AfterRegisterStates[2];
        AfterRegisterStates[3] = s.AfterRegisterStates[3];
        OpCode = s.OpCode;
        A = s.A;
        B = s.B;
        C = s.C;
    }

    public boolean OutputMatches(Sample s) {
        return s.AfterRegisterStates[0] == AfterRegisterStates[0] &&
                s.AfterRegisterStates[1] == AfterRegisterStates[1] &&
                s.AfterRegisterStates[2] == AfterRegisterStates[2] &&
                s.AfterRegisterStates[3] == AfterRegisterStates[3];
    }
}