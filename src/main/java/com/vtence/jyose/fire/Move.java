package com.vtence.jyose.fire;

public enum Move {
    Right {
        @Override
        public Pos from(Pos initial) {
            return initial.plus(new Pos(1, 0));
        }
    },
    Down {
        @Override
        public Pos from(Pos initial) {
            return initial.plus(new Pos(0, 1));
        }
    },
    Left {
        @Override
        public Pos from(Pos initial) {
            return initial.plus(new Pos(-1, 0));
        }
    },
    Up {
        @Override
        public Pos from(Pos initial) {
            return initial.plus(new Pos(0, -1));
        }
    };

    public abstract Pos from(Pos initial);
}
