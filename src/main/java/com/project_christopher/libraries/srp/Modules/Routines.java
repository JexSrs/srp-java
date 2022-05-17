package com.project_christopher.libraries.srp.Modules;

import com.project_christopher.libraries.srp.Components.HashFunction;
import com.project_christopher.libraries.srp.Components.Options;
import com.project_christopher.libraries.srp.Components.PrimeGroup;
import com.project_christopher.libraries.srp.Components.RandomBytes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Routines {

    public static RandomBytes randomBytes = numBytes -> {
        byte[] bytes = new byte[numBytes];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    };

    public static Map<String, HashFunction> Hash = new HashMap<>();
    public static Map<Integer, PrimeGroup> PrimeGroup = new HashMap<>();

    static {
        Hash.put("SHA1", data -> {
            try {
                return MessageDigest.getInstance("SHA-1").digest(data);
            }
            catch (NoSuchAlgorithmException ignored) {}
            return new byte[0];
        });
        Hash.put("SHA256", data -> {
            try {
                return MessageDigest.getInstance("SHA-256").digest(data);
            }
            catch (NoSuchAlgorithmException ignored) {}
            return new byte[0];
        });
        Hash.put("SHA384", data -> {
            try {
                return MessageDigest.getInstance("SHA-384").digest(data);
            }
            catch (NoSuchAlgorithmException ignored) {}
            return new byte[0];
        });
        Hash.put("SHA512", data -> {
            try {
                return MessageDigest.getInstance("SHA-512").digest(data);
            }
            catch (NoSuchAlgorithmException ignored) {}
            return new byte[0];
        });

        PrimeGroup.put(256, new PrimeGroup(
                new BigInteger("125617018995153554710546479714086468244499594888726646874671447258204721048803"),
                BigInteger.valueOf(2)
        ));
        PrimeGroup.put(512, new PrimeGroup(
                new BigInteger("11144252439149533417835749556168991736939157778924947037200268358613863350040339017097790259154750906072491181606044774215413467851989724116331597513345603"),
                BigInteger.valueOf(2)
        ));
        PrimeGroup.put(768, new PrimeGroup(
                new BigInteger("1087179135105457859072065649059069760280540086975817629066444682366896187793570736574549981488868217843627094867924800342887096064844227836735667168319981288765377499806385489913341488724152562880918438701129530606139552645689583147"),
                BigInteger.valueOf(2)
        ));
        PrimeGroup.put(1024, new PrimeGroup(
                new BigInteger("167609434410335061345139523764350090260135525329813904557420930309800865859473551531551523800013916573891864789934747039010546328480848979516637673776605610374669426214776197828492691384519453218253702788022233205683635831626913357154941914129985489522629902540768368409482248290641036967659389658897350067939"),
                BigInteger.valueOf(2)
        ));
        PrimeGroup.put(1536, new PrimeGroup(
                new BigInteger("1486998185923128292816507353619409521152457662596380074614818966810244974827752411420380336514078832314731499938313197533147998565301020797040787428051479639316928015998415709101293902971072960487527411068082311763171549170528008620813391411445907584912865222076100726050255271567749213905330659264908657221124284665444825474741087704974475795505492821585749417639344967192301749033325359286273431675492866492416941152646940908101472416714421046022696100064262587"),
                BigInteger.valueOf(2)
        ));
        PrimeGroup.put(2048, new PrimeGroup(
                new BigInteger("21766174458617435773191008891802753781907668374255538511144643224689886235383840957210909013086056401571399717235807266581649606472148410291413364152197364477180887395655483738115072677402235101762521901569820740293149529620419333266262073471054548368736039519702486226506248861060256971802984953561121442680157668000761429988222457090413873973970171927093992114751765168063614761119615476233422096442783117971236371647333871414335895773474667308967050807005509320424799678417036867928316761272274230314067548291133582479583061439577559347101961771406173684378522703483495337037655006751328447510550299250924469288819"),
                BigInteger.valueOf(2)
        ));
        PrimeGroup.put(3072, new PrimeGroup(
                new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A93AD2CAFFFFFFFFFFFFFFFF", 16),
                BigInteger.valueOf(5)
        ));
        PrimeGroup.put(4096, new PrimeGroup(
                new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A92108011A723C12A787E6D788719A10BDBA5B2699C327186AF4E23C1A946834B6150BDA2583E9CA2AD44CE8DBBBC2DB04DE8EF92E8EFC141FBECAA6287C59474E6BC05D99B2964FA090C3A2233BA186515BE7ED1F612970CEE2D7AFB81BDD762170481CD0069127D5B05AA993B4EA988D8FDDC186FFB7DC90A6C08F4DF435C934063199FFFFFFFFFFFFFFFF", 16),
                BigInteger.valueOf(5)
        ));
        PrimeGroup.put(6144, new PrimeGroup(
                new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A92108011A723C12A787E6D788719A10BDBA5B2699C327186AF4E23C1A946834B6150BDA2583E9CA2AD44CE8DBBBC2DB04DE8EF92E8EFC141FBECAA6287C59474E6BC05D99B2964FA090C3A2233BA186515BE7ED1F612970CEE2D7AFB81BDD762170481CD0069127D5B05AA993B4EA988D8FDDC186FFB7DC90A6C08F4DF435C93402849236C3FAB4D27C7026C1D4DCB2602646DEC9751E763DBA37BDF8FF9406AD9E530EE5DB382F413001AEB06A53ED9027D831179727B0865A8918DA3EDBEBCF9B14ED44CE6CBACED4BB1BDB7F1447E6CC254B332051512BD7AF426FB8F401378CD2BF5983CA01C64B92ECF032EA15D1721D03F482D7CE6E74FEF6D55E702F46980C82B5A84031900B1C9E59E7C97FBEC7E8F323A97A7E36CC88BE0F1D45B7FF585AC54BD407B22B4154AACC8F6D7EBF48E1D814CC5ED20F8037E0A79715EEF29BE32806A1D58BB7C5DA76F550AA3D8A1FBFF0EB19CCB1A313D55CDA56C9EC2EF29632387FE8D76E3C0468043E8F663F4860EE12BF2D5B0B7474D6E694F91E6DCC4024FFFFFFFFFFFFFFFF", 16),
                BigInteger.valueOf(5)
        ));
        PrimeGroup.put(8192, new PrimeGroup(
                new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A92108011A723C12A787E6D788719A10BDBA5B2699C327186AF4E23C1A946834B6150BDA2583E9CA2AD44CE8DBBBC2DB04DE8EF92E8EFC141FBECAA6287C59474E6BC05D99B2964FA090C3A2233BA186515BE7ED1F612970CEE2D7AFB81BDD762170481CD0069127D5B05AA993B4EA988D8FDDC186FFB7DC90A6C08F4DF435C93402849236C3FAB4D27C7026C1D4DCB2602646DEC9751E763DBA37BDF8FF9406AD9E530EE5DB382F413001AEB06A53ED9027D831179727B0865A8918DA3EDBEBCF9B14ED44CE6CBACED4BB1BDB7F1447E6CC254B332051512BD7AF426FB8F401378CD2BF5983CA01C64B92ECF032EA15D1721D03F482D7CE6E74FEF6D55E702F46980C82B5A84031900B1C9E59E7C97FBEC7E8F323A97A7E36CC88BE0F1D45B7FF585AC54BD407B22B4154AACC8F6D7EBF48E1D814CC5ED20F8037E0A79715EEF29BE32806A1D58BB7C5DA76F550AA3D8A1FBFF0EB19CCB1A313D55CDA56C9EC2EF29632387FE8D76E3C0468043E8F663F4860EE12BF2D5B0B7474D6E694F91E6DBE115974A3926F12FEE5E438777CB6A932DF8CD8BEC4D073B931BA3BC832B68D9DD300741FA7BF8AFC47ED2576F6936BA424663AAB639C5AE4F5683423B4742BF1C978238F16CBE39D652DE3FDB8BEFC848AD922222E04A4037C0713EB57A81A23F0C73473FC646CEA306B4BCBC8862F8385DDFA9D4B7FA2C087E879683303ED5BDD3A062B3CF5B3A278A66D2A13F83F44F82DDF310EE074AB6A364597E899A0255DC164F31CC50846851DF9AB48195DED7EA1B1D510BD7EE74D73FAF36BC31ECFA268359046F4EB879F924009438B481C6CD7889A002ED5EE382BC9190DA6FC026E479558E4475677E9AA9E3050E2765694DFC81F56E880B96E7160C980DD98EDD3DFFFFFFFFFFFFFFFFF", 16),
                BigInteger.valueOf(19)
        ));
    }


    private HashFunction hf;
    private PrimeGroup pg;
    private int NBits;

    public Routines apply(Options options) {
        this.pg = options.primeGroup != null ? options.primeGroup : Routines.PrimeGroup.get(2048);
        this.hf = options.hashFunction != null ? options.hashFunction : Routines.Hash.get("SHA512");
        this.NBits = this.pg.N.toString(2).length();
        return this;
    }

    /**
     * Generate a hash for multiple byte arrays.
     * @param arrays The byte arrays.
     */
    public byte[] hash(byte[] ...arrays) {
        return Utils.hash(this.hf, arrays);
    }

    /**
     * Left pad in ArrayBuffer with zeroes and generates a hash from it.
     * @param arrays The ArrayBuffers.
     */
    public byte[] hashPadded(byte[] ...arrays) {
        int targetLength = (int) Math.floor((this.NBits + 7) / 8f);
        return Utils.hashPadded(this.hf, targetLength, arrays);
    }

    /**
     * Computes K.
     */
    public BigInteger computeK() {
        return Transformations.byteArrayToBigint(
                this.hashPadded(
                        Transformations.bigintToByteArray(this.pg.N),
                        Transformations.bigintToByteArray(this.pg.g)
                )
        );
    }

    /**
     * Generates a random salt.
     * @param numBytes Length of salt in bytes.
     */
    public BigInteger generateRandomSalt(Integer numBytes) {
        int HBits = Utils.hashBitCount(this.hf);

        int saltBytes = numBytes != null ? numBytes : (2 * HBits) / 8;
        return Utils.generateRandomBigint(saltBytes);
    }

    /**
     * Computes X.
     * @param I The user's identity.
     * @param s The random salt.
     * @param P The user's password.
     */
    public BigInteger computeX(String I, BigInteger s, String P) {
        return Transformations.byteArrayToBigint(
                this.hash(
                        Transformations.bigintToByteArray(s),
                        this.computeIdentityHash(I, P)
                )
        );
    }

    /**
     * Computes X for step 2.
     * @param s The user's salt
     * @param identityHash The generated identity hash.
     */
    public BigInteger computeXStep2(BigInteger s, byte[] identityHash) {
        return Transformations.byteArrayToBigint(
                this.hash(
                        Transformations.bigintToByteArray(s),
                        identityHash
                )
        );
    }

    /**
     * Generates an identity based on user's Identity and Password.
     * @param I The user's identity.
     * @param P The user's password.
     */
    public byte[] computeIdentityHash(String I, String P) {
        return this.hash(Transformations.stringToByteArray(I+":"+P));
    }

    /**
     * Generates a verifier based on x.
     * @param x The x.
     */
    public BigInteger computeVerifier(BigInteger x) {
        return this.pg.g.modPow(x, this.pg.N);
    }

    /**
     * Generates private value for server (b) or client (a).
     */
    public BigInteger generatePrivateValue() {
        int numBytes = Math.max(256, this.NBits);
        BigInteger bi;

        do {
            bi = Utils.generateRandomBigint(numBytes / 8).mod(this.pg.N);
        }
        while (bi.equals(BigInteger.ZERO));

        return bi;
    }

    /**
     * Generates the public value for the client.
     * @param a The client's private value.
     */
    public BigInteger computeClientPublicValue(BigInteger a) {
        return this.pg.g.modPow(a, this.pg.N);
    }

    /**
     * Generates the public value for the client.
     * @param k The k.
     * @param v The verifier.
     * @param b The server's private value.
     */
    public BigInteger computeServerPublicValue(BigInteger k, BigInteger v, BigInteger b) {
        return this.pg.g
                .modPow(b, this.pg.N)
                .add(
                        v.multiply(k)
                )
                .mod(this.pg.N);
    }

    /**
     * Checks if public value is valid.
     * @param value The value.
     */
    public boolean isValidPublicValue(BigInteger value) {
        return !value.mod(this.pg.N).equals(BigInteger.ZERO);
    }

    /**
     * Computes U.
     * @param A The public value of client.
     * @param B The public value of server/\.
     */
    public BigInteger computeU(BigInteger A, BigInteger B) {
        return Transformations.byteArrayToBigint(
                this.hashPadded(
                        Transformations.bigintToByteArray(A),
                        Transformations.bigintToByteArray(B)
                )
        );
    }

    /**
     * Computes M1 which is the client's evidence.
     * @param I The user's identity.
     * @param s The random salt
     * @param A The client's public value.
     * @param B The server's public value.
     * @param S The session key.
     */
    public BigInteger computeClientEvidence(String I, BigInteger s, BigInteger A, BigInteger B, BigInteger S) {
        return Transformations.byteArrayToBigint(
                this.hash(
                        Transformations.stringToByteArray(I),
                        Transformations.bigintToByteArray(s),
                        Transformations.bigintToByteArray(A),
                        Transformations.bigintToByteArray(B),
                        Transformations.bigintToByteArray(S)
                )
        );
    }

    /**
     * Computes M2 which is the server's evidence.
     * @param A The client's public value.
     * @param M1 The client's evidence.
     * @param S The session key.
     */
    public BigInteger computeServerEvidence(BigInteger A, BigInteger M1, BigInteger S) {
        return Transformations.byteArrayToBigint(
                this.hash(
                        Transformations.bigintToByteArray(A),
                        Transformations.bigintToByteArray(M1),
                        Transformations.bigintToByteArray(S)
                )
        );
    }

    /**
     * Computes the session key S for the client.
     * @param k The k.
     * @param x The x.
     * @param u The u.
     * @param a The client's private value.
     * @param B The server's public value.
     */
    public BigInteger computeClientSessionKey(BigInteger k, BigInteger x, BigInteger u, BigInteger a, BigInteger B) {
        BigInteger N = this.pg.N;
        BigInteger exp = u.multiply(x).add(a);
        BigInteger tmp = this.pg.g.modPow(x, N).multiply(k).mod(N);

        return (B.add(N).subtract(tmp)).modPow(exp, N);
    }

    /**
     * Computes the session key S for the server.
     * @param v The verifier.
     * @param u The U.
     * @param A The client's public value.
     * @param b The server's private value.
     */
    public BigInteger computeServerSessionKey(BigInteger v, BigInteger u, BigInteger A, BigInteger b) {
        BigInteger N = this.pg.N;
        return v.modPow(u, N).multiply(A).modPow(b, N);
    }
}
